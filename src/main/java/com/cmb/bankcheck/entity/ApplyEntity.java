package com.cmb.bankcheck.entity;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-07-31
 * Time:11:20
 *  客户申请实体类
 */
public class ApplyEntity {

    private String userId;

    private String userName;

    private String msg;

    private String key;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
