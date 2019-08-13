package com.cmb.bankcheck.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-13
 * Time:11:54
 * 折扣申请单的字段与属性对应字典
 */
@Component
@PropertySource(value = {"classpath:apply-column-dict.properties"},encoding = "UTF-8")
@ConfigurationProperties(prefix="apply-column-dict")
public class ApplyColumnDict {


    private String userId;

    private String username;

    private String msg;

    private String processKey;

    // 发起人（员工）
    private String starter;
    // 给当前申请分配的id
    private String applyId;

    // 减免类型
    private String xmtype;

    // 减免金额
    private String amt;

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

    private String discountType;

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public String getStarter() {
        return starter;
    }

    public void setStarter(String starter) {
        this.starter = starter;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getXmtype() {
        return xmtype;
    }

    public void setXmtype(String xmtype) {
        this.xmtype = xmtype;
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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
