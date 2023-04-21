package com.java3y.hades.core.service.bootstrap;

import com.alibaba.fastjson2.JSON;
import com.google.common.base.Throwables;
import com.java3y.hades.core.constant.HadesConstant;
import com.java3y.hades.core.domain.MainConfig;
import com.java3y.hades.core.service.bean.RegisterBeanService;
import com.java3y.hades.core.service.config.HadesConfigProperties;
import com.java3y.hades.core.utils.GroovyUtils;
import com.java3y.hades.core.utils.HadesCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @author 3y
 */
@Slf4j
public abstract class BaseHadesConfig implements HadesConfig {


    @Autowired
    protected HadesConfigProperties configProperties;

    @Autowired
    private RegisterBeanService registerBeanService;

    @PostConstruct
    public void init() {
        String mainConfig = getConfigValueByName(HadesConstant.MAIN_CONFIG_NAME);
        if (StringUtils.hasText(mainConfig)) {
            bootstrap(mainConfig);
            addListener();
        }
    }

    /**
     * 1、解析主配置
     * 2、得到每个groovy配置并比对有无变化
     * 3、有变化的groovy配置重新注册
     *
     * @param mainConfig [{"domain":"austin","instanceNames":["austin.TencentSmsService"],"updateTime":"2023年3月20日10:26:0131"}]
     */
    public synchronized void bootstrap(String mainConfig) {
        try {
            for (MainConfig config : JSON.parseArray(mainConfig, MainConfig.class)) {
                if (config.getDomain().equals(configProperties.getDomain())) {
                    for (String instanceName : config.getInstanceNames()) {
                        String groovyCode = getConfigValueByName(instanceName);
                        if (StringUtils.hasText(groovyCode) && HadesCache.diff(instanceName, groovyCode)) {
                            register(instanceName, groovyCode);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("parseConfigAndRegister fail! config:{},exception:{}", mainConfig, Throwables.getStackTraceAsString(e));
        }

    }

    /**
     * 1、调用groovy加载器解析，生成class对象
     * 2、class对象注册到Spring IOC 容器中
     *
     * @param instanceName
     * @param groovyCode
     */
    public void register(String instanceName, String groovyCode) {
        Class clazz = GroovyUtils.parseClass(instanceName, groovyCode);
        if (Objects.nonNull(clazz)) {
            Object bean = registerBeanService.registerBean(instanceName, clazz);
            log.info("bean:[{}]已注册到Spring IOC中", bean.getClass().getName());
        }
    }
}
