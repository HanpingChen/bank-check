package com.cmb.bankcheck.service.impl;

import com.cmb.bankcheck.comon.StarterFactory;
import com.cmb.bankcheck.config.AppConfig;
import com.cmb.bankcheck.entity.*;
import com.cmb.bankcheck.mapper.CustomerMapper;
import com.cmb.bankcheck.message.Message;
import com.cmb.bankcheck.service.ActivitiService;
import com.cmb.bankcheck.service.CustomerService;
import com.cmb.bankcheck.starter.AbstractStarter;
import com.cmb.bankcheck.util.EntityConvertUtil;
import com.cmb.bankcheck.util.MessageUtil;
import com.cmb.bankcheck.util.TaskUtil;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-07-26
 * Time:10:43
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    AppConfig config;

    @Autowired
    CustomerMapper customerMapper;


    @Resource(name = "discount")
    AbstractStarter starter;

    @Autowired
    ActivitiService activitiService;

    @Override
    public Message startProcess(ApplyEntity apply) {
        String key = apply.getProcessKey();
        String userId = apply.getUserId();
        String Xmtype = apply.getXmtype();
        String starterId = apply.getStarter();
        double amt = apply.getAmt();
        System.out.println(key + ""+ Xmtype);
        if (key == null || userId == null || Xmtype == null || amt == 0 || starterId == null){
            Message msg = new Message();
            msg.setStatus(config.getErrorCode());
            msg.setMsg("字段不全");
            return msg;
        }
        // 根据流程的key动态加载流程启动类,如果没有找到，使用默认的starter（discount），实现类的name要和key对应
        AbstractStarter tempStarter = StarterFactory.createStarter(key);
        if (tempStarter != null){
            starter = tempStarter;
        }
        // 启动流程并初始化，包括设置流程变量，设置第一个任务的候选人等
        // 设置
        List<ProcessEntity> data = new ArrayList<>();
        ProcessEntity entity = starter.startAndInit(key, apply);
        data.add(entity);
        System.out.println(entity.getProcessId());
        return new MessageUtil<ProcessEntity>().setMsg(data, config.getSuccessCode(),config.getSuccessMsg());

    }

    @Override
    public Message queryProcess() {
        List<ProcessDefinition> list = activitiService.queryAllProcess();
        List<ProcessDefinitionEntity> data = new ArrayList<>();
        for (ProcessDefinition definition:list){
            ProcessDefinitionEntity entity = EntityConvertUtil.convertProcessDef(definition);
            data.add(entity);
        }
        return new MessageUtil<ProcessDefinitionEntity>().setMsg(data, config.getSuccessCode(),config.getErrorMsg());
    }

    @Override
    public Message queryProcessStatus(String userId) {
        // 首先查询process表中的process id
        List<ProcessEntity> processEntities = customerMapper.queryCustomerProcesses(userId);
        List<ProcessEntity> data = new ArrayList<>();
        try {
            for (ProcessEntity process:processEntities){
                String processId = process.getProcessId();
                // 查询任务
                Task task = activitiService.queryTaskByProcessId(processId);
                TaskEntity taskEntity = EntityConvertUtil.convertTask(task);
                List<String> candidates = activitiService.queryCandidateByTask(taskEntity.getTaskId());
                taskEntity.setCandidates(TaskUtil.convertCandidates(candidates));
                process.setTask(taskEntity);
                data.add(process);
            }
            return new MessageUtil<ProcessEntity>().setMsg(data, config.getSuccessCode(),config.getSuccessMsg());
        }catch (Exception e){
            Message msg = new Message();
            msg.setStatus(config.getErrorCode());
            msg.setMsg(e.getMessage());
            return msg;
        }

    }
}
