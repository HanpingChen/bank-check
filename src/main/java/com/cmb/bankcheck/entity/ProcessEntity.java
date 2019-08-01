package com.cmb.bankcheck.entity;

import java.util.Date;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-07-26
 * Time:11:20
 */
public class ProcessEntity {

    private String id;

    private String name;

    private String userId;

    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
