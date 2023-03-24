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
 * nacos 启动器
 */
@Service
@Slf4j
public class ApolloStarter extends BaseHadesConfig implements ConfigFileChangeListener {


    @Override
    public void addListener() {
        try {
            ConfigService.getConfigFile(configProperties.getFileName(), ConfigFileFormat.TXT).addChangeListener(this);
            log.info("分布式配置中心配置[{}]监听器已启动", configProperties.getFileName());
        } catch (Exception e) {
            log.error("HadesConfigService#refresh key:[{}] fail:{}", configProperties.getFileName(), Throwables.getStackTraceAsString(e));
        }
    }

    @Override
    public String getConfigValueByName(String fileName) {
        try {
            return ConfigService.getConfigFile(fileName, ConfigFileFormat.TXT).getContent();
        } catch (Exception e) {
            log.error("HadesConfigService#getConfigValueByName key:[{}],fail:{}", fileName, Throwables.getStackTraceAsString(e));
        }
        return null;
    }

    @Override
    public void onChange(ConfigFileChangeEvent changeEvent) {
        log.info("分布式配置中心监听到[{}]数据更新:{}", configProperties.getFileName(), changeEvent.getNewValue());
        bootstrap(changeEvent.getNewValue());
    }
}
