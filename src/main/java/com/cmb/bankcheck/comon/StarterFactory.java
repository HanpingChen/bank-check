package com.cmb.bankcheck.comon;

import com.cmb.bankcheck.starter.AbstractStarter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-09
 * Time:09:06
 * 生产启动类的工厂，实现spring管理接口
 */
@Component
public class StarterFactory implements ApplicationContextAware {

    private static Map<String, AbstractStarter> maps;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, AbstractStarter> beansOfType = applicationContext.getBeansOfType(AbstractStarter.class);
        maps = beansOfType;
    }

    public  static <T extends AbstractStarter> T createStarter(String starterName){
        return (T) maps.get(starterName);
    }

}
