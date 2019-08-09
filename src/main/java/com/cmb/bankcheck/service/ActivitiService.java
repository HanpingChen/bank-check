package com.cmb.bankcheck.service;

import com.cmb.bankcheck.entity.ProcessEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-08
 * Time:15:25
 */
public interface ActivitiService {

    ProcessInstance startProcessByKey(String key);

    Task queryTaskByProcessId(String processId);
}
