package com.java3y.hades.example.nacos.utils;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;

/**
 * @program: austin
 * @description:
 * @author: Giorno
 * @create: 2022-07-28
 **/
@Slf4j
@Component
public class NacosConfig {
    @Value("${hades.nacos.server}")
    private String nacosServer;
    @Value("${hades.nacos.username}")
    private String nacosUsername;
    @Value("${hades.nacos.password}")
    private String nacosPassword;
    @Value("${hades.nacos.namespace}")
    private String nacosNamespace;

    private ConfigService configService = null;

    @PostConstruct
    private void init() {
        Properties request = new Properties();
        request.put(PropertyKeyConst.SERVER_ADDR, nacosServer);
        request.put(PropertyKeyConst.NAMESPACE, nacosNamespace);
        request.put(PropertyKeyConst.USERNAME, nacosUsername);
        request.put(PropertyKeyConst.PASSWORD, nacosPassword);
        try {
            configService = NacosFactory.createConfigService(request);
        } catch (Exception e) {
            log.error("NacosConfig#init fail:{}", Throwables.getStackTraceAsString(e));
        }

    }

    public ConfigService getConfigService() {
        return configService;
    }

}
