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
            configService.addListener(configProperties.getFileName(), configProperties.getGroupName(), this);
            log.info("分布式配置中心配置[{}]监听器已启动", configProperties.getFileName());
        } catch (Exception e) {
            log.error("HadesConfigService#refresh key:[{}] fail:{}", configProperties.getFileName(), Throwables.getStackTraceAsString(e));
        }
    }

    @Override
    public String getConfigValueByName(String fileName) {
        try {

            return configService.getConfig(fileName, configProperties.getGroupName(), 3000L);
        } catch (NacosException e) {
            log.error("HadesConfigService#getConfigValueByName key:[{}],fail:{}", fileName, Throwables.getStackTraceAsString(e));
        }
        return null;
    }


    @Override
    public void receiveConfigInfo(String mainConfig) {
        log.info("分布式配置中心监听到[{}]数据更新:{}", configProperties.getFileName(), mainConfig);
        bootstrap(mainConfig);
    }

    @Override
    public Executor getExecutor() {
        return null;
    }
}
