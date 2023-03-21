package com.java3y.hades.example.nacos.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.google.common.base.Throwables;
import com.java3y.hades.core.service.BaseHadesConfigService;
import com.java3y.hades.core.service.HadesConfigService;
import com.java3y.hades.example.nacos.utils.NacosConfig;
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
public class NacosHadesConfigServiceImpl extends BaseHadesConfigService implements HadesConfigService {

    @Autowired
    private NacosConfig nacosConfig;

    @Value("${hades.nacos.data-id}")
    private String dataId;
    @Value("${hades.nacos.group}")
    private String group;

    @Override
    @PostConstruct
    public void init() {
        String mainConfig = getConfigValueByName(dataId);
        if (StrUtil.isNotBlank(mainConfig)) {
            parseConfigAndRegister(mainConfig);
            refresh();
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
                public void receiveConfigInfo(String mainConfig) {
                    log.info("分布式配置中心监听到[{}]数据更新:{}", dataId, mainConfig);
                    parseConfigAndRegister(mainConfig);
                }
            });
        } catch (Exception e) {
            log.error("HadesConfigService#refresh key:[{}] fail:{}", dataId, Throwables.getStackTraceAsString(e));
        }

    }

    @Override
    public String getConfigValueByName(String name) {
        try {
            return nacosConfig.getConfigService().getConfig(name, group, 3000L);
        } catch (NacosException e) {
            log.error("HadesConfigService#getConfigValueByName key:[{}],fail:{}", name, Throwables.getStackTraceAsString(e));
        }
        return null;
    }
}
