package com.cmb.bankcheck.service.impl;

import com.cmb.bankcheck.config.AppConfig;
import com.cmb.bankcheck.config.NewConfig;
import com.cmb.bankcheck.entity.ProcessEntity;
import com.cmb.bankcheck.entity.TaskEntity;
import com.cmb.bankcheck.mapper.ProcessMapper;
import com.cmb.bankcheck.message.Message;
import com.cmb.bankcheck.message.ResponseMessage;
import com.cmb.bankcheck.service.ApprovalService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.form.TaskElContext;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.lang.model.element.NestingKind;
import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-01
 * Time:13:07
 * 1.员工根据自己姓名（员工id)查询自己的任务，返回自己的姓名（id),代办的任务id,任务ins_id,任务名称
 * 2.
 */
@Service
public class ApprovalServiceImpl implements ApprovalService {


    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private AppConfig config;

    @Autowired
    private ProcessMapper processMapper;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private NewConfig newConfig;

    @Override
    public Message queryTask(String assignee) {
        if (assignee==null){
            Message msg=new Message();
            msg.setMsg("字段不全");
            msg.setStatus(config.getErrorCode());
            return msg;
        }

        TaskQuery taskQuery = taskService.createTaskQuery();
        taskQuery.taskAssignee(assignee);
        List<Task> taskList = taskQuery.list();
        ResponseMessage<TaskEntity> resMsg=new ResponseMessage<>();
        List<TaskEntity> data=new ArrayList<>();
        for (Task task:taskList) {
            TaskEntity entity=new TaskEntity();
            entity.setAssignee(assignee);
            //返回group
            entity.setGroupId(task.getCategory());
            entity.setTaskId(task.getId());
            entity.setUpdateTime(task.getCreateTime());
            entity.setTaskName(task.getName());
            entity.setProcessInstanceId(task.getProcessInstanceId());
            data.add(entity);
        }
        resMsg.setData(data);
        resMsg.setSize(data.size());
        resMsg.setStatus(config.getSuccessCode());
        resMsg.setMsg(config.getSuccessMsg());
        return resMsg;
    }

    @Override
    public Message startTask(String taskId,String judgement,String remark,String assignee) {
          /*根据传进来的任务id，以及审批人自己的决定来确定是否同意任务。
           *前段传进的参数包括，待执行任务id,决定：不/同意,审批意见，指定下一个任务审批人
           *1.删除当前任务
           *2.并更新历史数据*/

        Message msg=new Message();
        ResponseMessage<ProcessEntity> remsg = new ResponseMessage<>();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processId=task.getProcessInstanceId();
        String label =taskService.createTaskQuery().taskId(taskId).singleResult().getDescription();

        //会签任务
        if(label!=null && label.equals("sign")){
            int  nrOfInstances=(int) taskService.getVariables(taskId).get("nrOfInstances");
            int nrOfCompletedInstances=(int) taskService.getVariables(taskId).get("nrOfCompletedInstances");
            if(!judgement.equals("NO")){
                int pass_count = (int) taskService.getVariable(taskId,"count");
                pass_count  = pass_count + 1;
                taskService.setVariable(taskId, "count",pass_count);
                nrOfCompletedInstances++;
                taskService.complete(taskId);
            }else{
                nrOfCompletedInstances++;
                taskService.complete(taskId);
            }

            String printMes = "已完成人数 "+nrOfCompletedInstances;
            String nextTaskId = taskService.createTaskQuery().processInstanceId(processId).list().get(0).getId();
            //达到审批委员会的总审批人数，即委员会有人都审批完毕
            if (nrOfCompletedInstances==nrOfInstances){
                int count=(int) taskService.getVariables(nextTaskId).get("count");
                if(count<2){
                    //委员会通票数不够，删除任务。
                    runtimeService.deleteProcessInstance(processId,remark);
                    processMapper.updateProcessByProcessId(processId,remark,null,newConfig.getRefuseCode());
                    msg.setMsg(newConfig.getRefuseMsg()+printMes);
                    msg.setStatus(newConfig.getRefuseCode());
                    return  msg;
                }
                //会签任务通过
                msg.setMsg(newConfig.getCompleteMsg()+printMes);
                msg.setStatus(newConfig.getCompleteCode());
                return msg;
            }

            //非最后一个会签任务情况下，正常返回执行状态
            List<ProcessEntity> data =new ArrayList<>() ;
            ProcessEntity entity =new ProcessEntity();
            entity.setRemark(remark);
            //entity.setStatus(config.getSuccessCode());
            entity.setProcessId(processId);
            data.add(entity);
            remsg.setData(data);
            if (judgement.equals("NO")) {
                remsg.setMsg(newConfig.getRefuseMsg()+printMes);//"审批不通过"
                remsg.setStatus(newConfig.getRefuseCode());
                //！！！添加返回值！！！
                return remsg;
            }
            remsg.setMsg(newConfig.getCompleteMsg()+printMes);//"审批通过"
            remsg.setStatus(newConfig.getCompleteCode());
            return  remsg;
        }else{
            //非会签任务
            //审核不通过，删除任务，插入任务流程数据
            if (judgement.equals("NO")){
                //删除任务
                runtimeService.deleteProcessInstance(processId,remark);
                //从process表中根据流程定义流程Id查询得到该任务对应的userId
                List<ProcessEntity> processEntities=processMapper.querytProcessByProcessId(processId);
                ProcessEntity  entity=processEntities.get(0);
                String userId=entity.getUserId();
                //把相应的userId值加到process表中，改变这个实例的状态、不同意的原因等；-1代表流程不同意，任务结束
                processMapper.updateProcessByProcessId(processId,remark,null,newConfig.getRefuseCode());
                msg.setStatus(config.getErrorCode());
                msg.setMsg(newConfig.getRefuseMsg());//"审核不通过，当前任务结束"
                return msg;
            }

            //审核通过的情况下，任务执行，并且指定下一个任务的办理人
            taskService.complete(taskId);
            List<Task> nextTasks = taskService.createTaskQuery().processInstanceId(processId).list();
            System.setProperty("user.timezone","Asia/Shanghai");
            Date endTime=new Date();

            if (nextTasks == null){
                 /**1.需要更新process表，流程状态，结束时间
                  *2.更新完成返回process表的内容
                  */
                ProcessEntity entity=new ProcessEntity();
                processMapper.updateProcessByProcessId(processId,remark,endTime,newConfig.getEndCode());
                List<ProcessEntity> processEntities=processMapper.querytProcessByProcessId(processId);
                ProcessEntity  entityInProcessTable=processEntities.get(0);
                String userId=entityInProcessTable.getUserId();
                entity.setUserId(userId);
                entity.setProcessId(processId);
                //Date endTime = historyService.createHistoricTaskInstanceQuery().processInstanceId(processId).singleResult().getEndTime();
                entity.setCompleteTime(endTime);
                List<ProcessEntity> data=new ArrayList<>();
                data.add(entity);
                remsg.setData(data);
                remsg.setMsg(newConfig.getEndMsg());//"审批结束"
                remsg.setStatus(newConfig.getEndCode());
                return remsg;
            }

            /*指定下一个任务的办理人
            只有下一个任务为为非会签任务时，即任务只存在一个（会签任务会同时存在多个任务）*/
            if(nextTasks.size()==1) {
                taskService.setAssignee(nextTasks.get(0).getId(), assignee);
            }
            List<ProcessEntity> data =new ArrayList<>() ;
            ProcessEntity entity =new ProcessEntity();
            entity.setRemark(remark);
            entity.setStatus(config.getSuccessCode());
            entity.setProcessId(processId);
            entity.setCompleteTime(endTime);
            data.add(entity);
            remsg.setData(data);
            remsg.setMsg(newConfig.getCompleteMsg());//"审批通过"
            remsg.setStatus(newConfig.getCompleteCode());
            return remsg;
        }
    }

    //多人会签
  /*  public Message multiSign(String taskId,String judgement,String remark,String assignee){
        //获取任务标签
        String processId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId();
        //只要是会签任务，直接执行
        Message msg= startTask(taskId,judgement, remark, "");
        String nextTaskId = taskService.createTaskQuery().processInstanceId(processId).singleResult().getId();
//        Task currentTask = taskService.createTaskQuery().taskId(taskId).singleResult();
        String nextTaskLabel=taskService.createTaskQuery().taskId(nextTaskId).singleResult().getDescription();
        if (nextTaskLabel!="sign"){
            int count=(Integer) taskService.getVariables(taskId).get("count");
            //设置赞同票的数量
            if(count>6){
                taskService.deleteTask(nextTaskId);
            return msg;
            }
        }
        return msg;
    }*/


    @Override
    public Message roleManagement(String name, String group) {
        return null;
    }

}
