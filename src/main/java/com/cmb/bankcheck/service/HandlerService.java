package com.cmb.bankcheck.service;

import com.cmb.bankcheck.entity.EmployeeEntity;
import com.cmb.bankcheck.message.Message;
import com.cmb.bankcheck.message.ResponseMessage;

import java.util.List;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-07-27
 * Time:12:07
 */
public interface HandlerService {


    /**
     * 查询一个部门的所有的处理人
     * @param apart
     * @return
     */
    ResponseMessage<EmployeeEntity> queryHandlerByApart(String apart);

    /**
     * 给一个任务分配处理人
     * @param taskId 任务的id
     * @param handlerId 处理人的员工id
     * @return 消息对象，存储设置结果
     */
    Message setHandlerOfTask(String taskId, String handlerId);

    /**
     * 给一个任务分配多个处理人
     * @param taskId 任务id
     * @param handlerIds 处理人列表
     * @return 消息对象，存储设置结果
     */
    Message setHandlersOfTask(String taskId, List<String> handlerIds);
}
