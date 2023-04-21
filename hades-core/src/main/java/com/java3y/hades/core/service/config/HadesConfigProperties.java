package com.java3y.hades.core.service.config;

import com.java3y.hades.core.constant.HadesConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 3y
 * 项目配置信息
 */
@ConfigurationProperties(prefix = HadesConstant.PROPERTIES_PREFIX)
@Configuration
@Data
public class HadesConfigProperties {
    /**
     * 是否使用hades规则引擎
     */
    private Boolean enabled;

    /**
     * 配置域,相当于:nacos的group,apollo的namespace加上前缀
     */
    private String domain;


}
