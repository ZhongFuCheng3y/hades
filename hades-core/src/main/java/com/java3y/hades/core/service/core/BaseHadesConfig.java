package com.java3y.hades.core.service.core;

import com.alibaba.fastjson2.JSON;
import com.google.common.base.Throwables;
import com.java3y.hades.core.domain.MainConfig;
import com.java3y.hades.core.service.bean.RegisterBeanService;
import com.java3y.hades.core.utils.GroovyUtils;
import com.java3y.hades.core.utils.HadesCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @author 3y
 */
@Slf4j
public abstract class BaseHadesConfig implements HadesConfig {
    /**
     * 核心主配置名
     * apollo / nacos 共用
     */
    @Value("${hades.main.config.file-name}")
    protected String mainConfigFileName;

    /**
     * nacos 使用
     */
    @Value("${hades.main.config.group-name}")
    protected String nacosGroup;

    @Autowired
    private RegisterBeanService registerBeanService;

    @PostConstruct
    public void init() {
        String mainConfig = getConfigValueByName(mainConfigFileName);
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
     * @param mainConfig {"instanceNames":["TencentSmsService"],"updateTime":"2023年3月20日10:26:0131"}
     */
    public void bootstrap(String mainConfig) {
        try {
            MainConfig mainConfigVal = JSON.parseObject(mainConfig, MainConfig.class);
            for (String instanceName : mainConfigVal.getInstanceNames()) {
                String groovyCode = getConfigValueByName(instanceName);
                if (HadesCache.diff(instanceName, groovyCode)) {
                    register(instanceName, groovyCode);
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
