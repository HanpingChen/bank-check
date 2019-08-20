package com.cmb.bankcheck.service.impl;

import com.cmb.bankcheck.config.AppConfig;
import com.cmb.bankcheck.entity.ApplyEntity;
import com.cmb.bankcheck.mapper.ApplyMapper;
import com.cmb.bankcheck.message.Message;
import com.cmb.bankcheck.service.ApplyService;
import com.cmb.bankcheck.util.MessageUtil;
import jdk.jfr.DataAmount;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-02
 * Time:12:31
 */
@Service
public class ApplyServiceImpl implements ApplyService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ApplyMapper mapper;

    @Autowired
    AppConfig config;

    Map<String, String> map = new HashMap<>();

    @Override
    public ApplyEntity queryApplyDetailByProcessId(String processId) {

        if (runtimeService == null){
            System.out.println("runtime is null");
        }
        Map<String, Object> variables = runtimeService.getVariables(processId);
        System.out.println(variables);
        return null;
    }

    @Override
    public Message queryApply(String processId) {
        ApplyEntity entity = mapper.queryApplyByProcessId(processId);
        List<ApplyEntity> data = new ArrayList<>();
        data.add(entity);
        return new MessageUtil<ApplyEntity>().setMsg(data,config.getSuccessCode(),config.getSuccessMsg());
    }

    @Override
    public Message queryApplyColumnDict() {
        return null;
    }


}
