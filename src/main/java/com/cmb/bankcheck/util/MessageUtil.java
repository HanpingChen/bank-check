package com.cmb.bankcheck.util;

import com.cmb.bankcheck.message.Message;
import com.cmb.bankcheck.message.ResponseMessage;

import java.util.List;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-01
 * Time:16:16
 */
public class MessageUtil<T> {

    public ResponseMessage<T> setMsg(List<T> data, int status,String msg){
        ResponseMessage<T> message = new ResponseMessage<>();
        message.setMsg(msg);
        message.setStatus(status);
        message.setData(data);
        message.setSize(data.size());
        return message;
    }
}
