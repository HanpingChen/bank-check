package com.cmb.bankcheck.service;

import com.cmb.bankcheck.entity.ApplyEntity;
import com.cmb.bankcheck.message.Message;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-02
 * Time:12:29
 */
public interface ApplyService {

    /**
     * 通过流程的实例id查询流程实例的详情，也就是查询流程变量，并赋值给apply实体类
     * @param processId
     * @return
     */
    ApplyEntity queryApplyDetailByProcessId(String processId);

    /**
     * 通过申请单号查询
     * @param applyId
     * @return
     */
    Message queryApply(String applyId);

    /**
     * 查询apply申请单的字段与属性名对应的字典
     * @return
     */
    Message queryApplyColumnDict();
}
