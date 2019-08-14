package com.cmb.bankcheck.starter;

import com.cmb.bankcheck.entity.ApplyEntity;
import com.cmb.bankcheck.entity.EmployeeEntity;
import com.cmb.bankcheck.mapper.ApplyMapper;
import com.cmb.bankcheck.mapper.EmployeeMapper;
import com.cmb.bankcheck.mapper.ProcessMapper;
import com.cmb.bankcheck.service.ActivitiService;
import com.cmb.bankcheck.starter.AbstractStarter;
import com.cmb.bankcheck.util.BeanUtil;
import com.cmb.bankcheck.util.TaskUtil;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-08
 * Time:20:18
 * 减免审批流程的启动类，继承抽象启动类，实现具体的启动方法
 */
@Component("discount")
public class DiscountStarter extends AbstractStarter {
    @Autowired
    public void setRuntimeService(RuntimeService runtimeService){

        super.runtimeService = runtimeService;
    }

    @Autowired
    public void setActivitiService(ActivitiService activitiService){

        super.service = activitiService;
    }


    @Autowired
    public void setEmployeeMapper(EmployeeMapper employeeMapper){
        super.employeeMapper = employeeMapper;
    }
    @Autowired
    public void setProcessMapper(ProcessMapper processMapper){

        super.processMapper = processMapper;
    }

    @Autowired
    public void setTaskService(TaskService taskService){
        super.taskService = taskService;
    }

    @Autowired
    ApplyMapper applyMapper;

    @Autowired
    private ActivitiService activitiService;

    @Override
    Map<String, Object> initVariable(Object bean, String processId) {
        HashMap<String, Object> map = null;
        try {
            map = BeanUtil.convertBean(bean);
            System.out.println(map);
            // 还需要将委员会名单设置
            // 从数据库中查询管理委员会
            List<String> assignees = employeeMapper.queryEmployeeByApart("管理委员会");
            System.out.println(assignees);
            map.put("assignees",assignees);
            map.put("count",0);
            ApplyEntity entity = (ApplyEntity) bean;
            // 查询初始的经办人的信息，设置subbranch的信息
            String subbranch = employeeMapper.queryEmployeeInfo(entity.getStarter()).getSubbranch();
            map.put("subbranch",subbranch);
            this.runtimeService.setVariables(processId, map);
            entity.setApplyId(processId);
            // 写入数据库
            applyMapper.insertApply(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return map;
    }

    @Override
    List<String> setCandidates(Task task, Map<String, Object> map) {
        String taskId = task.getId();
        List<String> candidates = activitiService.queryCandidatesByTaskName(task);
        for (String candidate:candidates){
            taskService.addCandidateUser(taskId, candidate);
        }
        return candidates;
    }

    @Override
    public String toString() {
        return "this is discount starter";
    }
}
