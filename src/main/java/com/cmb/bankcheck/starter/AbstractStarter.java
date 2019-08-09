package com.cmb.bankcheck.starter;

import com.cmb.bankcheck.entity.ProcessEntity;
import com.cmb.bankcheck.mapper.EmployeeMapper;
import com.cmb.bankcheck.mapper.ProcessMapper;
import com.cmb.bankcheck.service.ActivitiService;
import com.cmb.bankcheck.service.impl.ActivitiServiceImpl;
import com.cmb.bankcheck.util.EntityConvertUtil;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Inherited;
import java.util.List;
import java.util.Map;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-08
 * Time:20:17
 * 模板方法，将启动流程实例的过程作为模板
 */
public abstract class AbstractStarter {

    protected ActivitiService service;

    protected ProcessMapper processMapper;

    protected RuntimeService runtimeService;

    protected EmployeeMapper employeeMapper;

    protected TaskService taskService;



    public ProcessEntity startAndInit(String key, Object bean){
        // 启动流程
        ProcessInstance processInstance = service.startProcessByKey(key);
        // 初始化流程变量
        Map<String, Object> map = this.initVariable(bean, processInstance.getId());
        if (map == null){
            return null;
        }
        // 将流程id和userid写入process表中
        processMapper.insertProcess((String) map.get("userId"), processInstance.getId(),1,"", processInstance.getStartTime(), null,null,processInstance.getParentId());
        // 获取当前任务并返回
        Task task = service.queryTaskByProcessId(processInstance.getId());
        // 设置候选人 也可以不设置直接返回null
        List<String> candidates = this.setCandidates(task, map);
        return EntityConvertUtil.convertProcess(processInstance, map, task,candidates);
    }

    abstract Map<String, Object> initVariable(Object bean, String processId);

    abstract List<String> setCandidates(Task task, Map<String, Object>map);
}
