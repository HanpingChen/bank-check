package com.cmb.bankcheck.controller;

import com.cmb.bankcheck.message.Message;
import com.cmb.bankcheck.service.LoginService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-06
 * Time:23:14
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;
    @RequestMapping(value = "/employ/login",method = RequestMethod.POST)
    public Message login(@Param("id")String id, @Param("password") String password){
        return loginService.login(id, password);
    }
}
