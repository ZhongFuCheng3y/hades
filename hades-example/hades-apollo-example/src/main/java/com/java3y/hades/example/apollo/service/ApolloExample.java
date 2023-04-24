package com.java3y.hades.example.apollo.service;

import com.ctrip.framework.apollo.ConfigFileChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.core.enums.ConfigFileFormat;
import com.ctrip.framework.apollo.model.ConfigFileChangeEvent;
import com.google.common.base.Throwables;
import com.java3y.hades.core.service.bootstrap.BaseHadesConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * @author 3y
 * apollo 启动器
 */
@Service
@Slf4j
public class ApolloExample extends BaseHadesConfig implements ConfigFileChangeListener {


    @Override
    public void addListener() {
        try {
            ConfigService.getConfigFile(configProperties.getConfigName(), ConfigFileFormat.TXT).addChangeListener(this);
            log.info("分布式配置中心配置[{}]监听器已启动", configProperties.getConfigName());
        } catch (Exception e) {
            log.error("HadesConfigService#refresh key:[{}] fail:{}", configProperties.getConfigName(), Throwables.getStackTraceAsString(e));
        }
    }

    @Override
    public String getConfigValueByName(String configName) {
        try {
            return ConfigService.getConfigFile(configName, ConfigFileFormat.TXT).getContent();
        } catch (Exception e) {
            log.error("HadesConfigService#getConfigValueByName key:[{}],fail:{}", configName, Throwables.getStackTraceAsString(e));
        }
        return null;
    }


    @Override
    public void onChange(ConfigFileChangeEvent changeEvent) {
        log.info("分布式配置中心监听到[{}]数据更新:{}", configProperties.getConfigName(), changeEvent.getNewValue());
        bootstrap(changeEvent.getNewValue());
    }

    @Override
    public void addOrUpdateProperty(String key, String value) {

    }
}
