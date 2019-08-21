package com.cmb.bankcheck.controller;

import com.cmb.bankcheck.entity.ApplyEntity;
import com.cmb.bankcheck.message.Message;
import com.cmb.bankcheck.service.CustomerService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-07-26
 * Time:09:59
 * 处理客户请求，包括开启流程、查询流程等，查看进度
 */
@RestController
public class CustomerController {

    @Autowired
    private CustomerService service;

    @RequestMapping(value = "/customer/start_process_by_key", method = RequestMethod.POST)
    public Message startProcessByKey(@RequestBody ApplyEntity apply){
        return service.startProcess(apply);
    }

    @RequestMapping(value = "/customer/query_process",method = RequestMethod.GET)
    public Message queryProcess(){
        return service.queryProcess();
    }

    @RequestMapping(value = "/customer/process_status",method = RequestMethod.POST)
    public Message queryProcessStatus(@Param("customerId") String customerId){
        return service.queryProcessStatus(customerId);
    }

    @RequestMapping(value="/customer/process_status_applyId",method = RequestMethod.POST)
    public Message queryProcessStatusByApplyId(String applyId){
        return service.queryProcessStatusByApplyId(applyId);
    }
}
