package com.cmb.bankcheck.message;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-07-27
 * Time:12:16
 */
public class Message {

    protected int status;
    protected String msg;

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

    @Override
    public String toString() {
        return "status:"+this.getStatus()+" msg:"+this.getMsg();
    }
}
