package com.cmb.bankcheck.service.impl;

import com.cmb.bankcheck.entity.ProcessEntity;
import com.cmb.bankcheck.message.Message;
import com.cmb.bankcheck.service.MultiTaskService;
import com.cmb.bankcheck.util.MessageUtil;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-05
 * Time:09:13
 */
@Service
public class MultiTaskServiceImpl implements MultiTaskService {
    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Override
    public Message startMultiTask(String assignees, String key) {
        String[] s = assignees.split(" ");
        List<String> assigneesList = new ArrayList<>();
        for (String a:s){
            assigneesList.add(a);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("assignees", assigneesList);
        map.put("count",0);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, map);
        List<ProcessEntity> data = new ArrayList<>();
        ProcessEntity entity = new ProcessEntity();
        entity.setProcessId(processInstance.getId());

        return new MessageUtil<ProcessEntity>().setMsg(data, 1, "success");
    }

    @Override
    public Message signTask(String taskId, String assignee) {
        String processId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId();

        int pass_count = (int) runtimeService.getVariable(processId,"count");
        pass_count  = pass_count + 1;
        runtimeService.setVariable(processId, "count",pass_count);
        String executionId = taskService.createTaskQuery().taskId(taskId).singleResult().getExecutionId();
        int nrOfCompleteInstances = (int) runtimeService.getVariable(executionId,"nrOfCompletedInstances") + 1;
        String msg = "已完成人数 "+nrOfCompleteInstances;
        taskService.complete(taskId);
        return new MessageUtil<ProcessEntity>().setMsg(new ArrayList<ProcessEntity>(),1,msg);
    }

}
