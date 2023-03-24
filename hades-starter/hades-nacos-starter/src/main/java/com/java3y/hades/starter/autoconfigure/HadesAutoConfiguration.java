package com.java3y.hades.starter.autoconfigure;


import com.java3y.hades.core.constant.HadesConstant;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * hades Nacos 自动配置类
 *
 * @author 3y
 */
@Configuration
@ConditionalOnClass(value = com.alibaba.nacos.api.config.ConfigService.class)
@ComponentScan(HadesConstant.SCAN_PATH)
public class HadesAutoConfiguration {


}
