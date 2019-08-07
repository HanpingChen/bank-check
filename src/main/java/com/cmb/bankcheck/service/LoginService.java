package com.cmb.bankcheck.service;

import com.cmb.bankcheck.message.Message;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-06
 * Time:23:16
 */
public interface LoginService {

    Message login(String id, String password);
}
