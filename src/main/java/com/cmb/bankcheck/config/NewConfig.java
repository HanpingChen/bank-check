package com.cmb.bankcheck.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = {"classpath:newApplication.properties"})
@ConfigurationProperties(prefix="newapp")
public class NewConfig {
    //审批不通过
    private int refuseCode;
    private  String refuseMsg;

    //审批通过
    private  int completeCode;
    private  String completeMsg;

    //审批结束
    private int endCode;
    private String endMsg ;

    //会签审核结果不通过
    private String signMsg;

    public int getRefuseCode() {
        return refuseCode;
    }

    public void setRefuseCode(int refuseCode) {
        this.refuseCode = refuseCode;
    }

    public String getRefuseMsg() {
        return refuseMsg;
    }

    public void setRefuseMsg(String refuseMsg) {
        this.refuseMsg = refuseMsg;
    }

    public int getCompleteCode() {
        return completeCode;
    }

    public void setCompleteCode(int completeCode) {
        this.completeCode = completeCode;
    }

    public String getCompleteMsg() {
        return completeMsg;
    }

    public void setCompleteMsg(String completeMsg) {
        this.completeMsg = completeMsg;
    }

    public int getEndCode() {
        return endCode;
    }

    public void setEndCode(int endCode) {
        this.endCode = endCode;
    }

    public String getEndMsg() {
        return endMsg;
    }

    public void setEndMsg(String endMsg) {
        this.endMsg = endMsg;
    }

    public String getSignMsg() {
        return signMsg;
    }

    public void setSignMsg(String signMsg) {
        this.signMsg = signMsg;
    }
}
