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

    // 发起人（员工）
    private String starter;
    // 给当前申请分配的id
    private String applyId;

    // 减免类型
    private String xmtype;

    // 减免金额
    private float amt;

    // 本年度的减免记录
    private String record;

    // 合作前景
    private String cor;

    // 效益评估与分析
    private String analyse;

    // 客户规模，效益有无不良贷款
    private String situation;

    // 办理机构
    private String branch;

    public String getApplyId() {
        return applyId;
    }

    public String getStarter() {
        return starter;
    }

    public void setStarter(String starter) {
        this.starter = starter;
    }

    public String getXmtype() {
        return xmtype;
    }

    public void setXmtype(String xmtype) {
        this.xmtype = xmtype;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }


    public float getAmt() {
        return amt;
    }

    public void setAmt(float amt) {
        this.amt = amt;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getAnalyse() {
        return analyse;
    }

    public void setAnalyse(String analyse) {
        this.analyse = analyse;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

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
