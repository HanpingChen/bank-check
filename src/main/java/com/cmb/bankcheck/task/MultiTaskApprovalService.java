package com.cmb.bankcheck.task;

import com.cmb.bankcheck.config.NewConfig;
import com.cmb.bankcheck.mapper.ProcessMapper;
import com.cmb.bankcheck.message.Message;
import com.cmb.bankcheck.service.ActivitiService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Autowired
    public ActivitiService activitiService;

    @Override
    public Message startTask(String taskId, String judgement, String remark){
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
        System.out.println(printMes);

        //达到审批委员会的总审批人数，即委员会有人都审批完毕
        if (nrOfCompletedInstances==nrOfInstances){
            String nextTaskId = taskService.createTaskQuery().processInstanceId(processId).list().get(0).getId();
            int count=(int) taskService.getVariables(nextTaskId).get("count");
            //从配置文件中加载会签通过的最低人数
            if(count<newConfig.getCount()){
                //委员会通票数不够，删除任务。
                deleteTask(processId,remark);
                String message=newConfig.getRefuseMsg()+printMes;
                int statusCode=newConfig.getRefuseCode();
                Message msg=returnMsg(processId,message,statusCode);
                return  msg;
            }
            //会签任务通过,并设置下一任务的候选处理人
            String message=newConfig.getCompleteMsg()+printMes;
            int statusCode=newConfig.getCompleteCode();
            Message msg=returnMsg(processId,message,statusCode);
            //设置候选人
            Task nextTask= taskService.createTaskQuery().processInstanceId(processId).list().get(0);
            String taskName = nextTask.getName();
            String branch=(String) taskService.getVariables(nextTask.getId()).get("branch");
            //获取候选人名单
            List<String> candidates = activitiService.queryCandidatesByTaskName(task);
            //设置候选人
            for (String candidate :candidates) {
                taskService.addCandidateUser(nextTask.getId(),candidate);
            }
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

