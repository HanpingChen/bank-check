package com.cmb.bankcheck.message;

import java.util.List;

/**
 * 查询用户的数据结果的返回信息格式
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-07-25
 * Time:13:51
 */
public class QueryUserMessage {

    private int status;


    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
