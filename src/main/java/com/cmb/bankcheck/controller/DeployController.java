package com.cmb.bankcheck.controller;

import com.cmb.bankcheck.service.DeployService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-07-26
 * Time:08:54
 * 部署流程的控制器，负责处理部署新流程的请求，需要的参数有流程文件和流程图片
 * 控制器提供两个接口，一个用户上传流程文件和流程图片，一个用于接收部署请求
 */
public class DeployController {

    @Autowired
    private DeployService service;



    public String deploy(){

        service.deploy();

        return "";
    }
}
