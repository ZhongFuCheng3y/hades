package com.java3y.hades.starter.config;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.google.common.base.Throwables;
import com.java3y.hades.core.service.bootstrap.BaseHadesConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;


/**
 * @author 3y
 * nacos 启动器
 */
@Service
@Slf4j
public class NacosStarter extends BaseHadesConfig implements Listener {

    @NacosInjected
    private ConfigService configService;

    @Override
    public void addListener() {
        try {
            configService.addListener(configProperties.getConfigName(), configProperties.getDomain(), this);
            log.info("分布式配置中心配置[{}]监听器已启动", configProperties.getConfigName());
        } catch (Exception e) {
            log.error("HadesConfigService#refresh key:[{}] fail:{}", configProperties.getConfigName(), Throwables.getStackTraceAsString(e));
        }
    }

    @Override
    public String getConfigValueByName(String configName) {
        try {

            return configService.getConfig(configName, configProperties.getDomain(), 3000L);
        } catch (NacosException e) {
            log.error("HadesConfigService#getConfigValueByName key:[{}],fail:{}", configName, Throwables.getStackTraceAsString(e));
        }
        return null;
    }


    @Override
    public void receiveConfigInfo(String mainConfig) {
        log.info("分布式配置中心监听到[{}]数据更新:{}", configProperties.getConfigName(), mainConfig);
        bootstrap(mainConfig);
    }

    @Override
    public Executor getExecutor() {
        return null;
    }
}
