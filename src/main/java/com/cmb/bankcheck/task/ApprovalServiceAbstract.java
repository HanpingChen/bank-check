package com.cmb.bankcheck.task;

import com.cmb.bankcheck.config.NewConfig;
import com.cmb.bankcheck.entity.ProcessEntity;
import com.cmb.bankcheck.mapper.ProcessMapper;
import com.cmb.bankcheck.message.Message;
import com.cmb.bankcheck.message.ResponseMessage;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class ApprovalServiceAbstract {

    @Autowired
    public TaskService taskService;

    @Autowired
    public RuntimeService runtimeService;

    @Autowired
    public ProcessMapper processMapper;

    @Autowired
    public NewConfig newConfig;

    //审核通过方法
    public void completeTask(String taskId){
        taskService.complete(taskId);
    }

    //审核不通过，删除任务，更新process表
    public void deleteTask(String processId,String remark){
        runtimeService.deleteProcessInstance(processId,remark);
        processMapper.updateProcessByProcessId(processId,remark,null,newConfig.getRefuseCode());
    }

    //任务执行，返回状态信息
    public Message returnMsg(String processId,String msg,int statusCode){
        ResponseMessage<ProcessEntity> remsg=new ResponseMessage<ProcessEntity>();
        ProcessEntity entity=new ProcessEntity();
        List<ProcessEntity> processEntities=processMapper.querytProcessByProcessId(processId);
        ProcessEntity  entityInProcessTable=processEntities.get(0);
        String userId=entityInProcessTable.getUserId();
        entity.setUserId(userId);
        entity.setProcessId(processId);
        entity.setCompleteTime(new Date());
        List<ProcessEntity> data=new ArrayList<>();
        data.add(entity);
        remsg.setData(data);
        remsg.setMsg(msg);
        remsg.setStatus(statusCode);
        return remsg;
    }

}
