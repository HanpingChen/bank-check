package com.cmb.bankcheck.task;

import com.cmb.bankcheck.config.NewConfig;
import com.cmb.bankcheck.mapper.ProcessMapper;
import com.cmb.bankcheck.message.Message;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("multiTask")
public class MultiTaskApprovalService extends ApprovalServiceAbstracter {
    /**
     * 执行会签任务
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
    public Message startTask(String taskId, String judgement, String remark,String assignee){
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processId=task.getProcessInstanceId();
        int nrOfInstances=(int) taskService.getVariables(taskId).get("nrOfInstances");
        int nrOfCompletedInstances=(int) taskService.getVariables(taskId).get("nrOfCompletedInstances");
        if(!judgement.equals("NO")){
            int pass_count = (int) taskService.getVariable(taskId,"count");
            pass_count  = pass_count + 1;
            taskService.setVariable(taskId, "count",pass_count);
            nrOfCompletedInstances++;
            completeTask(taskId);
        }else{
            nrOfCompletedInstances++;
            completeTask(taskId);
        }

        String printMes = "已完成人数 "+nrOfCompletedInstances;
        String nextTaskId = taskService.createTaskQuery().processInstanceId(processId).list().get(0).getId();
        //达到审批委员会的总审批人数，即委员会有人都审批完毕
        if (nrOfCompletedInstances==nrOfInstances){
            int count=(int) taskService.getVariables(nextTaskId).get("count");
            if(count<2){

                //委员会通票数不够，删除任务。
                deleteTask(processId,remark);
                String message=newConfig.getRefuseMsg()+printMes;
                int statusCode=newConfig.getRefuseCode();
                Message msg=returnMsg(processId,message,statusCode);
                return  msg;
            }
            //会签任务通过
            String message=newConfig.getCompleteMsg()+printMes;
            int statusCode=newConfig.getCompleteCode();
            Message msg=returnMsg(processId,message,statusCode);
            return msg;
        }

        //非最后一个会签任务情况下，正常返回执行状态
        if (judgement.equals("NO")) {
            String message=newConfig.getRefuseMsg()+printMes;//"审批不通过"
            int statusCode=newConfig.getRefuseCode();
            Message msg=returnMsg(processId,message,statusCode);
            return  msg;
        }
        String message=newConfig.getCompleteMsg()+printMes;//"审批通过"
        int statusCode=newConfig.getCompleteCode();
        Message msg=returnMsg(processId,message,statusCode);
        return  msg;
    }
}


