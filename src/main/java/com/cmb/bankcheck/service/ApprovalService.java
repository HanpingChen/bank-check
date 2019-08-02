package com.cmb.bankcheck.service;

import com.cmb.bankcheck.message.Message;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-01
 * Time:13:07
 */
public interface ApprovalService {
    //查询待办任务
    Message queryTask(String assignee);
    //办理任务
    Message startTask(String taskId,String judgement,String remark,String assignee);
    //设置角色
    Message roleManagement(String name,String group);

}
