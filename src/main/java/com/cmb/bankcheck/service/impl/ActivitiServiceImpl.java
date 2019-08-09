package com.cmb.bankcheck.service.impl;

import com.cmb.bankcheck.entity.ProcessEntity;
import com.cmb.bankcheck.mapper.EmployeeMapper;
import com.cmb.bankcheck.mapper.ProcessMapper;
import com.cmb.bankcheck.service.ActivitiService;
import com.cmb.bankcheck.util.BeanUtil;
import com.cmb.bankcheck.util.EntityConvertUtil;
import com.cmb.bankcheck.util.TaskUtil;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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



    @Override
    public ProcessInstance startProcessByKey(String key) {
        return runtimeService.startProcessInstanceByKey(key);
    }

    @Override
    public Task queryTaskByProcessId(String processId) {
        return taskService.createTaskQuery().processInstanceId(processId).singleResult();
    }
}
