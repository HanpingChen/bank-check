package com.cmb.bankcheck.controller;

import com.cmb.bankcheck.config.AppConfig;
import com.cmb.bankcheck.config.ApplyColumnDict;
import com.cmb.bankcheck.message.Message;
import com.cmb.bankcheck.service.ApplyService;
import com.cmb.bankcheck.util.MessageUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-13
 * Time:11:26
 * 申请单的请求控制器
 */

@RestController
public class ApplyController {

    @Autowired
    private ApplyService applyService;

    @Autowired
    private ApplyColumnDict dict;

    @Autowired
    private AppConfig config;

    @RequestMapping(value = "/apply/query_apply",method = RequestMethod.POST)
    public Message queryApply(@Param("applyId") String applyId){
        System.out.println("apply "+ applyId);
        return applyService.queryApply(applyId);
    }

    @RequestMapping(value = "/apply/query_dict",method = RequestMethod.GET)
    public Message queryColumnDict(){
        List<ApplyColumnDict> data = new ArrayList<>();
        data.add(dict);
        System.out.println(dict.getMsg());
        return new MessageUtil<ApplyColumnDict>().setMsg(data,config.getSuccessCode(),config.getSuccessMsg());
    }
}
