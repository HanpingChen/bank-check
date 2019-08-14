package com.cmb.bankcheck.service;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-08
 * Time:15:25
 */
public interface ActivitiService {

    /**
     * 通过流程的key启动流程
     * @param key
     * @return
     */
    ProcessInstance startProcessByKey(String key);

    /**
     * 通过流程id查询当前任务状态
     * @param processId
     * @return
     */
    Task queryTaskByProcessId(String processId);

    /**
     * 查询所有流程定义
     * @return
     */
    List<ProcessDefinition> queryAllProcess();

    /**
     * 通过员工号查询待办任务（处理人或候选人）
     * @param employeeId
     * @return
     */
    List<Task> queryTaskByCandidateOrAssignee(String employeeId);

    /**
     * 通过任务id查询任务的候选人
     * 调用这个方法的时候是当任务已经分配好候选人时才可调用
     * @param taskId
     * @return
     */
    List<String> queryCandidateByTask(String taskId);

    /**
     * 通过任务名字查询相应的处理候选人(此时任务还未设置候选人)
     * @param task
     * @return
     */
    List<String> queryCandidatesByTaskName(Task task);
}
