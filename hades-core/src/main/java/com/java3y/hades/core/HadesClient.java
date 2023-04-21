package com.java3y.hades.core;

import com.google.common.base.Throwables;
import groovy.lang.GroovyObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
     * 得到Spring IOC 容器里的对象(调用方以接口接收)
     *
     * @param instanceName
     * @param <T>
     * @return
     */
    public <T> T getInterfaceByName(String domain, String instanceName) {
        T bean = null;
        try {
            bean = (T) context.getBean(domain + "." + instanceName);
        } catch (Exception e) {
            log.error("HadesClient#getInterfaceByName get script name {} fail! e:{}", instanceName, Throwables.getStackTraceAsString(e));
        }
        return bean;
    }

    /**
     * 获取 GroovyObject
     *
     * @param instanceName
     * @return
     */
    public GroovyObject getGroovyObjectByName(String domain, String instanceName) {
        GroovyObject groovyObject = null;
        try {
            groovyObject = (GroovyObject) context.getBean(domain + "." + instanceName);
        } catch (Exception e) {
            log.error("HadesClient#getGroovyObjectByName get script name {} fail! e:{}", instanceName, Throwables.getStackTraceAsString(e));
        }
        return groovyObject;
    }

    /**
     * 调用执行： xxx对象 的 xxx方法 并 传入xxx方法的参数
     *
     * @param instanceName
     * @param methodName
     * @param params
     * @return 目标对象方法的返回值
     */
    public Object execute(String domain, String instanceName, String methodName, Object[] params) {
        Object resultObj = null;
        GroovyObject groovyObject = getGroovyObjectByName(domain, instanceName);
        try {
            if (Objects.nonNull(groovyObject)) {
                resultObj = groovyObject.invokeMethod(methodName, params);
            }
        } catch (Exception e) {
            log.error("HadesClient#execute fail! instanceName:[{}],methodName:[{}],params:[{}],exception:{}", instanceName, methodName, params, Throwables.getStackTraceAsString(e));
        }
        return resultObj;
    }
}
