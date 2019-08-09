package com.cmb.bankcheck.service;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-08
 * Time:15:25
 */
public interface ActivitiService {

    ProcessInstance startProcessByKey(String key);

    Task queryTaskByProcessId(String processId);

    List<ProcessDefinition> queryAllProcess();
}
