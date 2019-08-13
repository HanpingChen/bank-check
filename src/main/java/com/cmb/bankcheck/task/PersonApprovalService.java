package com.cmb.bankcheck.task;

import com.cmb.bankcheck.config.NewConfig;
import com.cmb.bankcheck.entity.ProcessEntity;
import com.cmb.bankcheck.mapper.ProcessMapper;
import com.cmb.bankcheck.message.Message;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
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

    @Override
    public Message startTask(String taskId, String judgement, String remark, String assignee) {
        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        String processId=task.getProcessInstanceId();
        //审核不通过，删除任务，插入任务流程数据
        if (judgement.equals("NO")){
            //删除任务
            //this.deleteTask(processId,remark);
            this.taskService.deleteTask(processId,remark);
            this.processMapper.updateProcessByProcessId(processId,remark,null,newConfig.getRefuseCode());
            String message="任务审核不通过";
            int statusCode=newConfig.getRefuseCode();
            Message msg=returnMsg(processId,message,statusCode);
            return msg;
        }

        //审核通过的情况下，任务执行，并且指定下一个任务的办理人
        //this.completeTask(taskId);
        taskService.complete(taskId);
        System.out.println(taskId+" "+processId);
        List<Task> nextTasks = taskService.createTaskQuery().processInstanceId(processId).list();
        System.out.println("............下一个任务.......................");
        System.out.println(nextTasks);
        System.out.println(".............................................");
        //任务通过，且流程结束
        if (nextTasks == null){
            /**1.需要更新process表，流程状态，结束时间
             *2.更新完成返回process表的内容
             */
            ProcessEntity entity=new ProcessEntity();
            System.out.println(".............完成任务，更新process表...........");
            this.processMapper.updateProcessByProcessId(processId,remark,new Date(),newConfig.getEndCode());
            System.out.println("..............完成任务，完成process表更新...........");
            String message="任务审核通过,当前任务办理结束";
            int statusCode=newConfig.getEndCode();
            Message msg=returnMsg(processId,message,statusCode);
            return msg;
        }

        /**指定下一个任务的办理人
         *只有下一个任务为为非会签任务时，即任务只存在一个（会签任务会同时存在多个任务）
         */
        if(nextTasks.size()==1) {
            this.taskService.setAssignee(nextTasks.get(0).getId(), assignee);
        }
        String message="任务审核通过";
        int statusCode=newConfig.getCompleteCode();
        Message msg=returnMsg(processId,message,statusCode);
        return msg;
    }
}

