package com.cmb.bankcheck.service.impl;

import com.cmb.bankcheck.config.AppConfig;
import com.cmb.bankcheck.entity.ProcessEntity;
import com.cmb.bankcheck.message.ResponseMessage;
import com.cmb.bankcheck.service.ProcessService;
import org.activiti.engine.RuntimeService;
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
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    AppConfig config;

    @Override
    public ResponseMessage<ProcessEntity> startProcessByKey(String key) {

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key);
        ResponseMessage<ProcessEntity> msg = new ResponseMessage<>();
        List<ProcessEntity> data = new ArrayList<>();
        ProcessEntity entity = new ProcessEntity();
        entity.setId(processInstance.getId());
        entity.setName(processInstance.getName());
        data.add(entity);
        msg.setData(data);
        msg.setSize(data.size());
        msg.setStatus(config.getSuccessCode());
        msg.setMsg(config.getSuccessMsg());
        return msg;
    }
}
