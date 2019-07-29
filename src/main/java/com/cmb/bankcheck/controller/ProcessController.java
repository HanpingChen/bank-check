package com.cmb.bankcheck.controller;

import com.cmb.bankcheck.entity.ProcessEntity;
import com.cmb.bankcheck.message.ResponseMessage;
import com.cmb.bankcheck.service.ProcessService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-07-26
 * Time:09:59
 * 用于处理有关流程的请求，包括开启流程、查询流程等
 */
@RestController
public class ProcessController {

    @Autowired
    private ProcessService service;

    @RequestMapping(value = "/start_process_by_key", method = RequestMethod.POST)
    public ResponseMessage<ProcessEntity> startProcessByKey(@Param("key") String key){
        return service.startProcessByKey(key);
    }
}
