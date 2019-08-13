package com.cmb.bankcheck.comon;

import com.cmb.bankcheck.service.ApprovalService;
import com.cmb.bankcheck.starter.AbstractStarter;
import com.cmb.bankcheck.task.ApprovalServiceAbstracter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class TaskFactory implements ApplicationContextAware {
    private static Map<String, ApprovalServiceAbstracter> maps;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, ApprovalServiceAbstracter> beansOfType = applicationContext.getBeansOfType(ApprovalServiceAbstracter.class);
        maps = beansOfType;
    }

    public  static <T extends ApprovalServiceAbstracter> T createTask(String taskClassName){
        return (T) maps.get(taskClassName);
    }

}
