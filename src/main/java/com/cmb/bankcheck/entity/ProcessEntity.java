package com.cmb.bankcheck.entity;

import org.activiti.engine.task.Task;

import java.util.Date;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-07-26
 * Time:11:20
 */
public class ProcessEntity {

    private String processId;

    private String name;

    private String userId;

    private Date createTime;

    // 当前任务
    private TaskEntity task;

    // 当前流程状态
    private int status;

    // 当前流程备注
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

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

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public TaskEntity getTask() {
        return task;
    }

    public void setTask(TaskEntity task) {
        this.task = task;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
