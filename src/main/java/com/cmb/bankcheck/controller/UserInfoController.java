package com.cmb.bankcheck.controller;

import com.cmb.bankcheck.mapper.UserMapper;
import com.cmb.bankcheck.message.QueryUserMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-07-25
 * Time:13:42
 */
@RestController
public class UserInfoController {

    @Autowired
    UserMapper mapper;


}
