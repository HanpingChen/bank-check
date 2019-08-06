package com.cmb.bankcheck.service.impl;

import com.cmb.bankcheck.entity.ApplyEntity;
import com.cmb.bankcheck.service.ApplyService;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public ApplyEntity queryApplyDetailByProcessId(String processId) {

        if (runtimeService == null){
            System.out.println("runtime is null");
        }
        Map<String, Object> variables = runtimeService.getVariables(processId);
        System.out.println(variables);
        return null;
    }
}
