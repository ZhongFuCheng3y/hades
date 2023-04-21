package com.java3y.hades.core.service.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author 3y
 * 将groovy class 注册到 Spring IOC 容器中
 */
@Service
@Slf4j
public class RegisterBeanService {

    @Autowired
    private ApplicationContext applicationContext;

    public <T> T registerBean(String name, Class<T> clazz, Object... args) {
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        for (Object arg : args) {
            beanDefinitionBuilder.addConstructorArgValue(arg);
        }
        BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) context.getBeanFactory();
        if (context.containsBean(name)) {
            log.info("bean:[{}]在系统中已存在,接下来对其进行替换操作", name);
            beanFactory.removeBeanDefinition(name);
        }
        beanFactory.registerBeanDefinition(name, beanDefinition);
        return applicationContext.getBean(name, clazz);
    }
}
