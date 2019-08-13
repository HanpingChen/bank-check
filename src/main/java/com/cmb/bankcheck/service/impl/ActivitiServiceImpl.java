package com.cmb.bankcheck.service.impl;

import com.cmb.bankcheck.mapper.EmployeeMapper;
import com.cmb.bankcheck.service.ActivitiService;
import com.cmb.bankcheck.util.TaskUtil;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private EmployeeMapper employeeMapper;



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

    @Override
    public List<Task> queryTaskByCandidateOrAssignee(String employeeId) {

        List<Task> tasks = taskService.createTaskQuery().taskCandidateOrAssigned(employeeId).list();
        return tasks;
    }

    @Override
    public List<String> queryCandidateByTask(String taskId) {
        List<String> candidates = new ArrayList<>();
        List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(taskId);
        for (IdentityLink link:identityLinksForTask){
            candidates.add(link.getUserId());
        }
        return candidates;
    }

    @Override
    public List<String> queryCandidatesByTaskName(String branch,String taskName) {
        // 获取任务名称中的部门名称
        String apart = TaskUtil.getApartNameFromTask(taskName);
        // 根据任务名称获取position
        String position = TaskUtil.getPosition(taskName);
        // 根据部门名称、机构代码查询对应的处理人
        List<String> candidates = employeeMapper.queryHandler(branch, apart, position);
        return candidates;
    }
}
