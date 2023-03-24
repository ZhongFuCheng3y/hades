package com.java3y.hades.core.service.config;

import com.java3y.hades.core.constant.HadesConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 3y
 * 项目配置信息
 */
@ConfigurationProperties(prefix = HadesConstant.PROPERTIES_PREFIX)
@Configuration
@Data
public class HadesConfigProperties {
    private String fileName;
    private String groupName;
}
