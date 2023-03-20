package com.java3y.hades.core;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * hades客户端
 *
 * @author 3y
 */
@Slf4j
@Component
public class HadesClient {

    @Autowired
    private ApplicationContext context;

    /**
     * 得到容器里的对象(该对象需要实现接口)
     *
     * @param scriptName
     * @param <T>
     * @return
     */
    public <T> T getInterfaceByName(String scriptName) {
        T bean = null;
        try {
            bean = (T) context.getBean(scriptName);
        } catch (Exception e) {
            log.error("com.java3y.hades.core.HadesClient#getInterfaceByName get script name {} fail! e:{}", scriptName, Throwables.getStackTraceAsString(e));
        }
        return bean;
    }
}
