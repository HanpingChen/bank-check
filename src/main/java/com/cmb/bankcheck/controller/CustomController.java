package com.cmb.bankcheck.controller;

import com.cmb.bankcheck.entity.ApplyEntity;
import com.cmb.bankcheck.message.Message;
import com.cmb.bankcheck.service.CustomService;
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
public class CustomController {

    @Autowired
    private CustomService service;

    @RequestMapping(value = "/custom/start_process_by_key", method = RequestMethod.POST)
    public Message startProcessByKey(@RequestBody ApplyEntity apply){
        return service.startProcess(apply);
    }

    @RequestMapping(value = "/custom/query_process",method = RequestMethod.GET)
    public Message queryProcess(){
        return service.queryProcess();
    }
}
