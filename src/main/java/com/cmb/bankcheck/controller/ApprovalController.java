package com.cmb.bankcheck.controller;

import com.cmb.bankcheck.message.Message;
import com.cmb.bankcheck.message.ResponseMessage;
import com.cmb.bankcheck.service.ApprovalService;
import org.apache.ibatis.annotations.Param;
import org.springframework.aop.target.SingletonTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-07-29
 * Time:14:34
 * 员工处理办理任务类的请求
 */
@RestController
public class ApprovalController {

    @Autowired
    private ApprovalService approvalService;

    @RequestMapping(value = "/employ/query_task_by_assignee", method = RequestMethod.POST)
    public Message queryTaskByAssignee(@Param("assignee") String assignee){
       return approvalService.queryTask(assignee);
    }

    @RequestMapping(value = "/employ/start_task", method = RequestMethod.POST)
    public Message startTask(@Param("taskId") String taskId,@Param("judgement") String judgement,@Param("remark") String remark){
     return approvalService.startTask(taskId,judgement,remark);
    }

    @RequestMapping(value ="/employ/acquire_task",method =RequestMethod.POST)
    public  Message acquireTask(@Param("taskId") String taskId, @Param("assignee") String assignee){
        return approvalService.acquireTask(taskId,assignee);

    }
}
