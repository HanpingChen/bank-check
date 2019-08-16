package com.cmb.bankcheck.task;

import com.cmb.bankcheck.config.NewConfig;
import com.cmb.bankcheck.entity.ProcessEntity;
import com.cmb.bankcheck.mapper.ProcessMapper;
import com.cmb.bankcheck.message.Message;
import com.cmb.bankcheck.service.ActivitiService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.swing.plaf.basic.BasicListUI;
import javax.swing.text.StyledEditorKit;
import java.util.Date;
import java.util.List;

@Component("personTask")
public class PersonApprovalService extends ApprovalServiceAbstracter {
    /**
     * 个人任务办理
     */

    @Autowired
    public void  setTaskService (TaskService taskService){
        super.taskService=taskService;
    }

    @Autowired
    public  void setRuntimeService (RuntimeService runtimeService){
        super.runtimeService=runtimeService;
    }

    @Autowired
    public  void setProcessMapper (ProcessMapper processMapper){
        super.processMapper=processMapper;
    }

    @Autowired
    public void setNewConfig (NewConfig newConfig){
        super.newConfig=newConfig;
    }

    @Autowired
    public ActivitiService activitiService;

    @Override
    public Message startTask(String taskId, String judgement, String remark) {
        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        String processId=task.getProcessInstanceId();
        //审核不通过，删除任务，插入任务流程数据
        if (judgement.toUpperCase().equals("NO")){
            //删除任务
            this.deleteTask(processId,remark);
            String message="任务审核不通过";
            int statusCode=newConfig.getRefuseCode();
            Message msg=returnMsg(processId,message,statusCode);
            return msg;
        }

        //审核通过的情况下，任务执行，并且指定下一个任务的办理人
        //this.completeTask(taskId);
        this.taskService.complete(taskId);
        System.out.println("打印nextTasks");
        List<Task> nextTasks = this.taskService.createTaskQuery().processInstanceId(processId).list();
        System.out.println("nextTasks为:"+nextTasks);
        //任务通过，且流程结束
        if (nextTasks.size()==0){
            /**1.需要更新process表，流程状态，结束时间
             *2.更新完成返回process表的内容
             */
            ProcessEntity entity=new ProcessEntity();
            this.processMapper.updateProcessByProcessId(processId,remark,new Date(),newConfig.getEndCode());
            String message="任务审核通过,当前任务办理结束";
            int statusCode=newConfig.getEndCode();
            Message msg=returnMsg(processId,message,statusCode);
            return msg;
        }

        /**指定下一个任务的办理人，系统查询数据库，根据任务的名称和branch来确定下个任务的候选处理人，并指定这些候选处理人
         *只有下一个任务为为非会签任务时，即任务只存在一个（会签任务会同时存在多个任务）
         */
        if(nextTasks.size()==1) {
            Task nextTask= nextTasks.get(0);
            String taskName = nextTask.getName();
            String branch=(String) taskService.getVariables(nextTask.getId()).get("branch");
            //获取候选人名单
            List<String> candidates = activitiService.queryCandidatesByTaskName(nextTask);
            System.out.println("普通任务候选人"+candidates);
            //设置候选人
            for (String candidate :candidates) {
                taskService.addCandidateUser(nextTask.getId(),candidate);
            }
            System.out.println("候选人设置成功");
        }

        String message="任务审核通过";
        int statusCode=newConfig.getCompleteCode();
        Message msg=returnMsg(processId,message,statusCode);
        return msg;
    }
}

