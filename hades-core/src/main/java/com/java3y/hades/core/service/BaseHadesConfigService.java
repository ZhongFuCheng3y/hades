package com.java3y.hades.core.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.java3y.hades.core.domain.MainConfig;
import groovy.lang.GroovyClassLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 3y
 */
@Slf4j
public abstract class BaseHadesConfigService implements HadesConfigService {

    private static GroovyClassLoader groovyClassLoader = new GroovyClassLoader();

    @Autowired
    private RegisterBeanService registerBeanService;

    /**
     * 解析主配置并注册到Spring容器里
     *
     * @param mainConfig {"instanceNames":["TencentSmsService"],"updateTime":"2023年3月20日10:26:0131"}
     */
    public void parseConfigAndRegister(String mainConfig) {
        try {
            MainConfig mainConfigVal = JSON.parseObject(mainConfig, MainConfig.class);
            for (String instanceName : mainConfigVal.getInstanceNames()) {
                String groovyCode = getConfigValueByName(instanceName);
                if (StrUtil.isNotBlank(groovyCode)) {
                    register(instanceName, groovyCode);
                }
            }
        } catch (Exception e) {
            log.error("parseConfigAndRegister fail! config:{},exception:{}", mainConfig, Throwables.getStackTraceAsString(e));
        }

    }

    public void register(String instanceName, String groovyCode) {
        Class groovyClass = groovyClassLoader.parseClass(groovyCode);
        Object bean = registerBeanService.registerBean(instanceName, groovyClass);
        log.info("bean---{}", bean.getClass().getName());
    }
}
