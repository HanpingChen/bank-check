package com.cmb.bankcheck.service.impl;

import com.cmb.bankcheck.config.AppConfig;
import com.cmb.bankcheck.entity.ApplyEntity;
import com.cmb.bankcheck.entity.ProcessDefinitionEntity;
import com.cmb.bankcheck.entity.ProcessEntity;
import com.cmb.bankcheck.mapper.ProcessMapper;
import com.cmb.bankcheck.message.Message;
import com.cmb.bankcheck.message.ResponseMessage;
import com.cmb.bankcheck.service.CustomService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
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
public class CustomServiceImpl implements CustomService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    AppConfig config;

    @Autowired
    ProcessMapper processMapper;

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
        processMapper.insertProcess(userId, processInstance.getId());
        // 返回数据
        List<ProcessEntity> data = new ArrayList<>();
        ProcessEntity entity = new ProcessEntity();
        entity.setId(processInstance.getId());
        entity.setName(processInstance.getName());
        entity.setUserId(userId);
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
}
