package com.cmb.bankcheck.service.impl;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.cmb.bankcheck.comon.TaskFactory;
import com.cmb.bankcheck.config.AppConfig;
import com.cmb.bankcheck.config.NewConfig;
import com.cmb.bankcheck.entity.ProcessEntity;
import com.cmb.bankcheck.entity.TaskEntity;
import com.cmb.bankcheck.mapper.ProcessMapper;
import com.cmb.bankcheck.message.Message;
import com.cmb.bankcheck.message.ResponseMessage;
import com.cmb.bankcheck.service.ActivitiService;
import com.cmb.bankcheck.service.ApprovalService;
import com.cmb.bankcheck.task.ApprovalServiceAbstracter;
import com.cmb.bankcheck.util.BeanUtil;
import com.cmb.bankcheck.util.EntityConvertUtil;
import com.cmb.bankcheck.util.MessageUtil;
import com.cmb.bankcheck.util.TaskUtil;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.form.TaskElContext;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.lang.model.element.NestingKind;
import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-01
 * Time:13:07
 * 1.员工根据自己姓名（员工id)查询自己的任务，返回自己的姓名（id),代办的任务id,任务ins_id,任务名称
 * 2.
 */
@Service
public class ApprovalServiceImpl implements ApprovalService {

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private AppConfig config;

    @Autowired
    private ProcessMapper processMapper;

    @Autowired
    private NewConfig newConfig;

    @Autowired
    ActivitiService activitiService;

    //默认初始化任务为普通任务
    @Resource(name="personTask")
    private ApprovalServiceAbstracter taskInstance;


    @Override
    public Message queryTask(String assignee) {
        if (assignee==null){
            Message msg=new Message();
            msg.setMsg("字段不全");
            msg.setStatus(config.getErrorCode());
            return msg;
        }

        List<Task> taskList = activitiService.queryTaskByCandidateOrAssignee(assignee);
        List<TaskEntity> data=new ArrayList<>();
        for (Task task:taskList) {
            TaskEntity taskEntity = EntityConvertUtil.convertTask(task);
            List<String> candidates = activitiService.queryCandidateByTask(taskEntity.getTaskId());
            taskEntity.setCandidates(TaskUtil.convertCandidates(candidates));
            data.add(taskEntity);
        }
        return new MessageUtil<TaskEntity>().setMsg(data, config.getSuccessCode(),config.getSuccessMsg());
    }

    @Override
    public Message startTask(String taskId,String judgement,String remark,String assignee) {
          /*根据传进来的任务id，以及审批人自己的决定来确定是否同意任务。
           *前段传进的参数包括，待执行任务id,决定：不/同意,审批意见，指定下一个任务审批人
           *1.删除当前任务
           *2.并更新历史数据*/

        if (taskId==null || judgement==null ){
            Message msg=new Message();
            msg.setMsg("字段不全");
            msg.setStatus(config.getErrorCode());
            return msg;
        }

        /**
         *  任务执行，工厂类动态加载相关任务类，默认是普通任务personTask，动态加载其他类（这里其他类只有一个会签任务类）
         * 注意，默认类需要重新手动加载，否在会使用上一个类型任务创建的实例
         */

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String label =task.getDescription();
        ApprovalServiceAbstracter tempStarter = TaskFactory.createTask(label);
        if (tempStarter!=null){
           taskInstance = tempStarter;
        }else {
            taskInstance=TaskFactory.createTask("personTask");
        }
        Message msg=taskInstance.startTask( taskId,judgement,remark,assignee);
        return msg;
    }

    @Override
    public Message roleManagement(String name, String group) {
        return null;
    }

}
