package com.cmb.bankcheck.listener;

import com.cmb.bankcheck.mapper.EmployeeMapper;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-07-26
 * Time:15:57
 */
@Service
public class MultiCheckListener implements TaskListener {

    @Autowired
    private RuntimeService runtimeService;

    @Resource
    EmployeeMapper employeeMapper;

    @Override
    public void notify(DelegateTask delegateTask) {

        if (employeeMapper == null){
            System.out.println("mapper is null");
        }
        String id = delegateTask.getProcessInstanceId();
        String taskId = delegateTask.getId();
        System.out.println(taskId);
        runtimeService = ProcessEngines.getDefaultProcessEngine().getRuntimeService();
        System.out.println("监听器执行");
        if (runtimeService == null){
            System.out.println("没有注入");
            return;
        }
        Map<String, Object> map = runtimeService.getVariables(id);
        System.out.println(map);
//        if(map.get("xmtype").equals("1")){
//            runtimeService.deleteProcessInstance(id,"");
//        }
    }
}
