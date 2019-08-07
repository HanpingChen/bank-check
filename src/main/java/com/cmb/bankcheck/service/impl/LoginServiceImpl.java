package com.cmb.bankcheck.service.impl;

import com.cmb.bankcheck.mapper.EmployeeMapper;
import com.cmb.bankcheck.message.Message;
import com.cmb.bankcheck.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-06
 * Time:23:16
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    EmployeeMapper mapper;

    @Override
    public Message login(String id, String password) {
        // 查询数据库
        String truePassword = mapper.queryPassword(id);
        Message msg = new Message();
        if (truePassword == null){
            msg.setStatus(1);
            msg.setMsg("用户不存在");
        }else {
            if(truePassword.equals(password)){
                msg.setMsg("登录成功");
                msg.setStatus(0);
            }else {
                msg.setStatus(1);
                msg.setMsg("密码错误");
            }
        }
        return msg;
    }
}
