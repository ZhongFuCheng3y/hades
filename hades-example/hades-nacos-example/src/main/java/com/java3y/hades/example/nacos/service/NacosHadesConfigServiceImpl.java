package com.java3y.hades.example.nacos.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.google.common.base.Throwables;
import com.java3y.hades.core.domain.ListenerConfig;
import com.java3y.hades.core.service.HadesConfigService;
import com.java3y.hades.core.service.RegisterBeanService;
import com.java3y.hades.example.nacos.utils.NacosConfig;
import groovy.lang.GroovyClassLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;


/**
 * @author 3y
 * nacos 监听配置刷新
 */
@Service
@Slf4j
public class NacosHadesConfigServiceImpl implements HadesConfigService {
    private static GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
    @Autowired
    private NacosConfig nacosConfig;
    @Autowired
    private RegisterBeanService registerBeanService;

    @Value("${hades.nacos.data-id}")
    private String dataId;
    @Value("${hades.nacos.group}")
    private String group;

    @Override
    @PostConstruct
    public void init() {
        try {
            String config = nacosConfig.getConfigService().getConfig(dataId, group, 1000);
            ListenerConfig listenerConfig = JSON.parseObject(config, ListenerConfig.class);
            for (String instanceName : listenerConfig.getInstanceNames()) {
                register(instanceName);
            }
            refresh();
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refresh() {
        try {
            nacosConfig.getConfigService().addListener(dataId, group, new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }
                @Override
                public void receiveConfigInfo(String value) {
                    log.info("监听到数据更新:{}", value);
                    ListenerConfig listenerConfig = JSON.parseObject(value, ListenerConfig.class);
                    for (String instanceName : listenerConfig.getInstanceNames()) {
                        register(instanceName);
                    }
                }
            });
        } catch (Exception e) {
            log.error(e.toString());
        }

    }


    public void register(String beanName)  {
        try {
            String groovy = nacosConfig.getConfigService().getConfig(beanName, group, 1000);
            Class groovyClass = groovyClassLoader.parseClass(groovy);
            Object bean = registerBeanService.registerBean(beanName, groovyClass);
            log.info("bean---{}", bean.getClass().getName());
        } catch (Exception e) {
            log.error("register fail:{}", Throwables.getStackTraceAsString(e));
        }

    }
}
