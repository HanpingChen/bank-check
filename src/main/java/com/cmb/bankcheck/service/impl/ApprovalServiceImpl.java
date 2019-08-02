package com.cmb.bankcheck.service.impl;

import com.cmb.bankcheck.config.AppConfig;
import com.cmb.bankcheck.entity.ProcessEntity;
import com.cmb.bankcheck.entity.TaskEntity;
import com.cmb.bankcheck.mapper.ProcessMapper;
import com.cmb.bankcheck.message.Message;
import com.cmb.bankcheck.message.ResponseMessage;
import com.cmb.bankcheck.service.ApprovalService;
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

import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.List;

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
        if (judgement=="NO"){
            //从process表中根据流程定义流程Id查询得到该任务对应的userId
            List<ProcessEntity> processEntities=processMapper.queryProcessByUser(processId);
           // ProcessEntity proEn=new ProcessEntity();

            ProcessEntity  entity=processEntities.get(0);
            String userId=entity.getUserId();
            //把相应的userId值加到process表中，改变这个实例的状态、不同意的原因等；-1代表流程不同意，任务结束
            processMapper.insertProcess(userId, processId,-1,remark,null," ",null,null);
            msg.setStatus(config.getErrorCode());
            msg.setMsg("审核不通过，当前任务结束");
            return msg;
        }

        //审核通过的情况下，任务执行，并且指定下一个任务的办理人
        taskService.complete(taskId);
        Task nextTask = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        taskService.setAssignee(nextTask.getId(), assignee);
        //nextTask.setAssignee(assignee);
        List<ProcessEntity> data =new ArrayList<>() ;
        ProcessEntity entity =new ProcessEntity();
        entity.setRemark(remark);
        entity.setStatus(config.getSuccessCode());
        entity.setProcessId(processId);
        //String proName = runtimeService.createProcessInstanceQuery().processDefinitionId(processId).singleResult().getName();
        // entity.setName(proName);
        data.add(entity);
        remsg.setData(data);
        remsg.setMsg("审批通过");
        remsg.setStatus(config.getSuccessCode());
        return remsg;
    }

    @Override
    public Message roleManagement(String name, String group) {
        return null;
    }

}
