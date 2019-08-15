package com.cmb.bankcheck.service.impl;

import com.cmb.bankcheck.mapper.EmployeeMapper;
import com.cmb.bankcheck.service.ActivitiService;
import com.cmb.bankcheck.util.BranchUtil;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Set<String> candidates = new HashSet<>();
        List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(taskId);
        for (IdentityLink link:identityLinksForTask){
            candidates.add(link.getUserId());
        }
        return new ArrayList<>(candidates);
    }

    @Override
    public List<String> queryCandidatesByTaskName(Task task) {
        String taskName = task.getName();
        String branch = (String) taskService.getVariable(task.getId(),"branch");
        String subbranch = (String) taskService.getVariable(task.getId(),"subbranch");
        System.out.println(subbranch+" "+branch);
        // 获取任务名称中的部门名称
        String apart = TaskUtil.getApartNameFromTask(taskName);
        // 获取当前任务审批所在的机构类型，包括
        String taskBranchType = TaskUtil.getBranchTypeFromTaskName(taskName);
        if (apart == null){
            // 从任务中无法截取出部门名称，意味着当前任务还处于网点审批阶段，所以从流程变量中获取网点名称作为部门
            apart = subbranch;
        }
        if (apart.equals("二级分行")){
            apart = BranchUtil.getBranchName(branch);
        }
        if (taskBranchType.equals("二级分行") || apart.equals("管理委员会")){
            subbranch = apart;
        }
        if (taskBranchType.equals("一级分行")){
            subbranch = apart;
            if (!branch.equals("0551")){
                branch = "0551";
            }
        }
        // 根据任务名称获取position
        String position = TaskUtil.getPosition(taskName);
        System.out.println(branch +" "+subbranch+" "+apart+" "+position);
        // 根据部门名称、机构代码查询对应的处理人
        return employeeMapper.queryHandler(branch, subbranch,apart, position);
    }
}
