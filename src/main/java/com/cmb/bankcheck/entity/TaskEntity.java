package com.cmb.bankcheck.entity;

import java.sql.Timestamp;
import java.util.Date;

public class TaskEntity {

    private String taskId;

    private String taskName;

    private String assignee;

    private String groupId;

    private Date updateTime;

    // 当前任务所属的机构（一级分行和二级分行）
    private String currentBranch;

    // 当前任务所属的网点/部门
    private String currentSubbranch;
    // 所属的流程实例的id
    private String processInstanceId;

    // 时间字符串
    private String timeStr;

    // 流程的key
    private String processKey;

    // 候选人
    private String candidates;

    public String getCurrentBranch() {
        return currentBranch;
    }

    public void setCurrentBranch(String currentBranch) {
        this.currentBranch = currentBranch;
    }

    public String getCurrentSubbranch() {
        return currentSubbranch;
    }

    public void setCurrentSubbranch(String currentSubbranch) {
        this.currentSubbranch = currentSubbranch;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getCandidates() {
        return candidates;
    }

    public void setCandidates(String candidates) {
        this.candidates = candidates;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
