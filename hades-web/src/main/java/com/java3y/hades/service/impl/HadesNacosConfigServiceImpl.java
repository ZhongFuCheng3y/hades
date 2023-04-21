package com.java3y.hades.service.impl;


import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.google.common.base.Throwables;
import com.java3y.hades.core.constant.HadesConstant;
import com.java3y.hades.core.service.config.HadesConfigProperties;
import com.java3y.hades.service.HadesConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;


/**
 * @author 3y
 * 读取配置实现类
 */
@Service
@Slf4j
@ConditionalOnProperty(name = "nacos.config.enabled", havingValue = "true")
public class HadesNacosConfigServiceImpl implements HadesConfigService {

    @Autowired
    protected HadesConfigProperties configProperties;

    @NacosInjected
    private ConfigService configService;

    @Override
    public String getProperty(String key, String defaultValue) {
        try {
            return configService.getConfig(HadesConstant.MAIN_CONFIG_NAME, configProperties.getDomain(), 5000);
        } catch (Exception e) {
            log.error("HadesConfigService#getProperty fail:{}", Throwables.getStackTraceAsString(e));
        }
        return defaultValue;
    }

    @Override
    public void setProperty(String key, String value) {

    }
}
