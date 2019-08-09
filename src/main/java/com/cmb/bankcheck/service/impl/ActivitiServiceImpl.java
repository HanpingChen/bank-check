package com.cmb.bankcheck.service.impl;

import com.cmb.bankcheck.service.ActivitiService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-08
 * Time:15:26
 * 根据业务对一些activiti的操作进行封装
 */
@Service
public class ActivitiServiceImpl implements ActivitiService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private RepositoryService repositoryService;



    @Override
    public ProcessInstance startProcessByKey(String key) {
        return runtimeService.startProcessInstanceByKey(key);
    }

    @Override
    public Task queryTaskByProcessId(String processId) {
        return taskService.createTaskQuery().processInstanceId(processId).singleResult();
    }

    @Override
    public List<ProcessDefinition> queryAllProcess() {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        processDefinitionQuery.latestVersion();
        return processDefinitionQuery.list();
    }
}
