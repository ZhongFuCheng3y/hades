package com.java3y.hades.service.config;


import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.google.common.base.Throwables;
import com.java3y.hades.core.constant.HadesConstant;
import com.java3y.hades.core.service.bootstrap.BaseHadesConfig;
import com.java3y.hades.core.service.config.HadesConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;


/**
 * @author 3y
 * Nacos配置实现类
 */
@Service
@Slf4j
@ConditionalOnProperty(name = "nacos.config.enabled", havingValue = "true")
public class HadesNacosConfigServiceImpl extends BaseHadesConfig implements Listener {
    @Autowired
    protected HadesConfigProperties configProperties;

    @NacosInjected
    private ConfigService configService;

    @Override
    public void addOrUpdateProperty(String key, String value) {
        try {
            configService.publishConfig(key, HadesConstant.NACOS_DEFAULT_GROUP, value);
        } catch (Exception e) {
            log.error("HadesConfigService#addOrUpdateProperty fail:{}", Throwables.getStackTraceAsString(e));
        }
    }

    @Override
    public void removeProperty(String key)  {
        try {
            configService.removeConfig(key, HadesConstant.NACOS_DEFAULT_GROUP);
        } catch (NacosException e) {
            log.error("HadesConfigService#removeProperty fail:{}", Throwables.getStackTraceAsString(e));
        }
    }

    @Override
    public String getConfigValueByName(String configName) {
        try {
            return configService.getConfig(configName, HadesConstant.NACOS_DEFAULT_GROUP, 3000L);
        } catch (NacosException e) {
            log.error("HadesConfigService#getConfigValueByName key:[{}],fail:{}", configName, Throwables.getStackTraceAsString(e));
        }
        return null;
    }

    @Override
    public void receiveConfigInfo(String mainConfig) {
    }

    @Override
    public void addListener() {
    }

    @Override
    public Executor getExecutor() {
        return null;
    }
}
