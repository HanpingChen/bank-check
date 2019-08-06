package com.cmb.bankcheck.service;

import com.cmb.bankcheck.message.Message;

import java.util.List;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-05
 * Time:09:12
 */
public interface MultiTaskService {

    /**
     * 开启多实例任务
     * @param assignees
     * @param processId
     * @return
     */
    Message startMultiTask(String assignees, String processId);

    Message signTask(String taskId, String assignee);
}
