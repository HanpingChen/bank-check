package com.cmb.bankcheck.controller;

import com.cmb.bankcheck.message.Message;
import com.cmb.bankcheck.service.MultiTaskService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-05
 * Time:09:18
 */
@RestController
public class MultiTaskController {
    @Autowired
    MultiTaskService service;

    @RequestMapping("process/start_multi_task")
    public Message startMultiTask(@Param("assignees") String assignees,@Param("key") String key){
        return service.startMultiTask(assignees, key);
    }

    @RequestMapping("process/sign_task")
    public Message signTask(@Param("taskId") String taskId, @Param("assignee") String assignee){
        return service.signTask(taskId, assignee);
    }
}
