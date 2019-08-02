package com.cmb.bankcheck.service.impl;

import com.cmb.bankcheck.config.AppConfig;
import com.cmb.bankcheck.entity.ApplyEntity;
import com.cmb.bankcheck.entity.ProcessDefinitionEntity;
import com.cmb.bankcheck.entity.ProcessEntity;
import com.cmb.bankcheck.entity.TaskEntity;
import com.cmb.bankcheck.mapper.CustomerMapper;
import com.cmb.bankcheck.mapper.ProcessMapper;
import com.cmb.bankcheck.message.Message;
import com.cmb.bankcheck.message.ResponseMessage;
import com.cmb.bankcheck.service.CustomerService;
import com.cmb.bankcheck.util.MessageUtil;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-07-26
 * Time:10:43
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @Autowired
    AppConfig config;

    @Autowired
    ProcessMapper processMapper;

    @Autowired
    CustomerMapper customerMapper;

    @Override
    public Message startProcess(ApplyEntity apply) {
        String key = apply.getKey();
        String userId = apply.getUserId();
        if (key == null || userId == null){
            Message msg = new Message();
            msg.setStatus(config.getErrorCode());
            msg.setMsg("字段不全");
            return msg;
        }
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key);
        ResponseMessage<ProcessEntity> msg = new ResponseMessage<>();
        // 将流程id和userid写入process表中
        processMapper.insertProcess(userId, processInstance.getId(),1,"");
        /* 返回数据 */
        List<ProcessEntity> data = new ArrayList<>();
        ProcessEntity entity = new ProcessEntity();
        entity.setProcessId(processInstance.getId());
        entity.setName(processInstance.getName());
        entity.setUserId(userId);
        entity.setCreateTime(processInstance.getStartTime());
        data.add(entity);
        msg.setData(data);
        msg.setSize(data.size());
        msg.setStatus(config.getSuccessCode());
        msg.setMsg(config.getSuccessMsg());
        return msg;
    }

    @Override
    public Message queryProcess() {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        processDefinitionQuery.latestVersion();
        List<ProcessDefinition> list = processDefinitionQuery.list();
        List<ProcessDefinitionEntity> data = new ArrayList<>();
        for (ProcessDefinition definition:list){
            ProcessDefinitionEntity entity = new ProcessDefinitionEntity();
            entity.setKey(definition.getKey());
            entity.setId(definition.getId());
            entity.setName(definition.getName());
            entity.setVersion(definition.getVersion());
            data.add(entity);
        }
        ResponseMessage<ProcessDefinitionEntity> msg = new ResponseMessage<>();
        msg.setMsg(config.getSuccessMsg());
        msg.setStatus(config.getSuccessCode());
        msg.setData(data);
        msg.setSize(data.size());
        return msg;
    }

    @Override
    public Message queryProcessStatus(String userId) {
        // 首先查询process表中的process id
        List<ProcessEntity> processEntities = customerMapper.queryCustomerProcesses(userId);
        List<ProcessEntity> data = new ArrayList<>();
        try {
            for (ProcessEntity process:processEntities){
                String processId = process.getProcessId();
                // 查询任务
                TaskQuery taskQuery = taskService.createTaskQuery();
                taskQuery.processInstanceId(processId);
                List<Task> taskList = taskQuery.list();
                Task task = taskList.get(0);
                TaskEntity taskEntity = new TaskEntity();
                taskEntity.setAssignee(task.getAssignee());
                taskEntity.setTaskName(task.getName());
                taskEntity.setTaskId(task.getId());
                process.setTask(taskEntity);
                data.add(process);
            }

            return new MessageUtil<ProcessEntity>().setMsg(data, config.getSuccessCode(),config.getSuccessMsg());
        }catch (Exception e){
            Message msg = new Message();
            msg.setStatus(config.getErrorCode());
            msg.setMsg(e.getMessage());
            return msg;
        }
    }
}
