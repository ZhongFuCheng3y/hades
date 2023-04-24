//package com.java3y.hades.service.impl;
//
//
//import com.ctrip.framework.apollo.Config;
//import com.ctrip.framework.apollo.ConfigService;
//import com.ctrip.framework.apollo.core.enums.ConfigFileFormat;
//import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
//import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
//import com.google.common.base.Throwables;
//import com.java3y.hades.core.constant.HadesConstant;
//import com.java3y.hades.core.service.config.HadesConfigProperties;
//import com.java3y.hades.service.HadesConfigService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.stereotype.Service;
//
//
///**
// * @author 3y
// * TODO Apollo配置实现类（未完成）
// */
//@Service
//@Slf4j
//@ConditionalOnProperty(name = "apollo.bootstrap.enabled", havingValue = "true")
//public class HadesApolloConfigServiceImpl implements HadesConfigService {
//
//    @Autowired
//    protected HadesConfigProperties configProperties;
//    @Value("apollo.portal.address")
//    private String address;
//
//    @Override
//    public void init() {
//
//    }
//
//    @Override
//    public String getProperty(String key, String defaultValue) {
//        try {
//            return ConfigService.getConfigFile(configProperties.getConfigName(), ConfigFileFormat.TXT).getContent();
//        } catch (Exception e) {
//            log.error("HadesConfigService#getProperty fail:{}", Throwables.getStackTraceAsString(e));
//        }
//        return defaultValue;
//    }
//
//    @Override
//    public void addOrUpdateProperty(String key, String value) {
//
//    }
//}
