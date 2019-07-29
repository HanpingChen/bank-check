package com.cmb.bankcheck.service;

import com.cmb.bankcheck.entity.ProcessEntity;
import com.cmb.bankcheck.message.ResponseMessage;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-07-26
 * Time:10:42
 */
public interface ProcessService {

    ResponseMessage<ProcessEntity> startProcessByKey(String key);
}
