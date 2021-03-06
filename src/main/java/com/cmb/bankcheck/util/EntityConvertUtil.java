package com.cmb.bankcheck.util;

import com.cmb.bankcheck.entity.ProcessDefinitionEntity;
import com.cmb.bankcheck.entity.ProcessEntity;
import com.cmb.bankcheck.entity.TaskEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-08
 * Time:16:53
 * activiti中的bean转化为自定义的entity实体类的工具类
 */
public class EntityConvertUtil {

    public static ProcessEntity convertProcess(ProcessInstance instance, Map<String,Object> map, Task task, List<String> candidates){
        ProcessEntity entity = new ProcessEntity();
        entity.setProcessId(instance.getId());
        entity.setCreateTime(instance.getStartTime());
        entity.setUserId((String) map.get("userId"));
        entity.setName(instance.getProcessDefinitionName());
        entity.setStatus((Integer) map.getOrDefault("status",0));
        entity.setApplyId((String) map.get("applyId"));
        TaskEntity taskEntity = convertTask(task);
        if (candidates!=null) {
            StringBuilder sb = new StringBuilder();
            for (int i=0;i<candidates.size();i++){
                sb.append(candidates.get(i));
                if (i != candidates.size()-1){
                    sb.append(",");
                }
            }
            taskEntity.setCandidates(sb.toString());
        }
        entity.setTask(taskEntity);
        return entity;
    }

    public static TaskEntity convertTask(Task task){
        TaskEntity entity = new TaskEntity();
        entity.setAssignee(task.getAssignee());
        //返回group
        entity.setGroupId(task.getName());
        entity.setTaskId(task.getId());
        entity.setUpdateTime(task.getCreateTime());
        entity.setProcessKey(task.getProcessDefinitionId().split(":")[0]);
        entity.setTaskName(task.getName()+"审批");
        // 设置时间字符串
        entity.setTimeStr(TimeUtil.convertDateToString(task.getCreateTime()));
        entity.setProcessInstanceId(task.getProcessInstanceId());
        return entity;

    }

    public static ProcessDefinitionEntity convertProcessDef(ProcessDefinition definition){
        ProcessDefinitionEntity entity = new ProcessDefinitionEntity();
        entity.setKey(definition.getKey());
        entity.setId(definition.getId());
        entity.setName(definition.getName());
        entity.setVersion(definition.getVersion());
        return entity;
    }
}
