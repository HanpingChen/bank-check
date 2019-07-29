package com.cmb.bankcheck.service.impl;

import com.cmb.bankcheck.entity.EmployeeEntity;
import com.cmb.bankcheck.message.Message;
import com.cmb.bankcheck.message.ResponseMessage;
import com.cmb.bankcheck.service.HandlerService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-07-27
 * Time:12:08
 * 处理人相关服务的实现类
 */
@Service
public class HandlerServiceImpl implements HandlerService {

    @Override
    public ResponseMessage<EmployeeEntity> queryHandlerByApart(String apart) {

        return null;
    }

    @Override
    public Message setHandlerOfTask(String taskId, String handlerId) {
        return null;
    }

    @Override
    public Message setHandlersOfTask(String taskId, List<String> handlerIds) {
        return null;
    }
}
