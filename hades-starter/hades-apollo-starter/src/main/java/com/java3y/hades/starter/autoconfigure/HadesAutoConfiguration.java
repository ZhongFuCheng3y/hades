package com.java3y.hades.starter.autoconfigure;


import com.java3y.hades.core.constant.HadesConstant;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static com.ctrip.framework.apollo.spring.config.PropertySourcesConstants.APOLLO_BOOTSTRAP_ENABLED;


/**
 * hades apollo 自动配置类
 *
 * @author 3y
 */
@Configuration
@ConditionalOnClass(value = com.ctrip.framework.apollo.ConfigService.class)
@ConditionalOnProperty(value = {HadesConstant.HADES_ENABLED_PROPERTIES, APOLLO_BOOTSTRAP_ENABLED}, havingValue = "true")
@ComponentScan(HadesConstant.SCAN_PATH)
public class HadesAutoConfiguration {

}
