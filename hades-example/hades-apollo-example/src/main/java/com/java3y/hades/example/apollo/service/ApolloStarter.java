package com.java3y.hades.example.apollo.service;

import com.ctrip.framework.apollo.ConfigFileChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.core.enums.ConfigFileFormat;
import com.ctrip.framework.apollo.model.ConfigFileChangeEvent;
import com.google.common.base.Throwables;
import com.java3y.hades.core.service.core.BaseHadesConfig;
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
            ConfigService.getConfigFile(mainConfigFileName, ConfigFileFormat.TXT).addChangeListener(this);
            log.info("分布式配置中心配置[{}]监听器已启动", mainConfigFileName);
        } catch (Exception e) {
            log.error("HadesConfigService#refresh key:[{}] fail:{}", mainConfigFileName, Throwables.getStackTraceAsString(e));
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
        log.info("分布式配置中心监听到[{}]数据更新:{}", mainConfigFileName, changeEvent.getNewValue());
        bootstrap(changeEvent.getNewValue());
    }
}
