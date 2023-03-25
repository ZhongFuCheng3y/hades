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
     * 主配置名
     * nacos / apollo 通用
     */
    private String fileName;

    /**
     * nacos专用
     * groupName
     */
    private String groupName;


}
