package com.java3y.hades.service.impl;


import cn.hutool.core.io.file.FileReader;
import com.alibaba.fastjson2.JSON;
import com.google.common.base.Throwables;
import com.java3y.hades.core.domain.MainConfig;
import com.java3y.hades.core.service.bootstrap.HadesBaseConfig;
import com.java3y.hades.core.service.config.HadesConfigProperties;
import com.java3y.hades.domain.HadesParam;
import com.java3y.hades.enums.RuleType;
import com.java3y.hades.parse.ParseConfig;
import com.java3y.hades.service.HadesConfigService;
import com.java3y.hades.vo.HadesConfigListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 3y
 * 配置实现类
 */
@Service
@Slf4j
public class HadesConfigServiceImpl implements HadesConfigService {

    @Autowired
    private HadesBaseConfig baseConfigService;

    @Autowired
    private HadesConfigProperties configProperties;

    @Autowired
    private ParseConfig parseConfig;


    @Override
    public void addConfig(HadesParam hadesParam) {
        try {
            // 主配置
            MainConfig mainConfig = JSON.parseObject(baseConfigService.getConfigValueByName(configProperties.getConfigName()), MainConfig.class);
            mainConfig.getInstanceNames().add(hadesParam.getName());
            mainConfig.setUpdateTime(System.currentTimeMillis());
            baseConfigService.addOrUpdateProperty(configProperties.getConfigName(), JSON.toJSONString(mainConfig));

            // 脚本配置
            String resultCode;
            if (RuleType.CODE.getCode().equals(hadesParam.getRuleType())) {
                resultCode = hadesParam.getRuleLogicCode();
            } else {
                String template = new FileReader("generateGroovyCodeTemplate").readString();
                resultCode = template.replace("_PACKAGE_NAME_", hadesParam.getName().substring(0, hadesParam.getName().lastIndexOf(".")))
                        .replace("_SCRIPT_NAME_", hadesParam.getName())
                        .replace("_CLASS_NAME_", hadesParam.getName().substring(hadesParam.getName().lastIndexOf(".") + 1))
                        .replace("_CONDITION_", parseConfig.parse(JSON.toJSONString(hadesParam.getRuleLogicGraph())));
                baseConfigService.addOrUpdateProperty(hadesParam.getName(), resultCode);
            }
            baseConfigService.addOrUpdateProperty(hadesParam.getName(), resultCode);

        } catch (Exception e) {
            log.error("HadesConfigService#addConfig fail:{}", Throwables.getStackTraceAsString(e));
        }
    }

    @Override
    public List<HadesConfigListVo> getAllConfig(String keywords) {
        List<HadesConfigListVo> result = new ArrayList<>();
        try {
            MainConfig mainConfig = JSON.parseObject(baseConfigService.getConfigValueByName(configProperties.getConfigName()), MainConfig.class);
            for (String instanceName : mainConfig.getInstanceNames()) {
                if (StringUtils.hasText(keywords) && !instanceName.contains(keywords)) {
                    continue;
                }
                HadesConfigListVo hadesConfigListVo = HadesConfigListVo.builder().name(instanceName).ruleLogicCode(baseConfigService.getConfigValueByName(instanceName)).build();
                result.add(hadesConfigListVo);
            }
        } catch (Exception e) {
            log.error("HadesConfigService#getAllConfig fail:{}", Throwables.getStackTraceAsString(e));
        }

        return result;
    }

    @Override
    public void deleteConfig(String name) {
        try {
            MainConfig mainConfig = JSON.parseObject(baseConfigService.getConfigValueByName(configProperties.getConfigName()), MainConfig.class);
            mainConfig.getInstanceNames().remove(name);
            baseConfigService.addOrUpdateProperty(configProperties.getConfigName(), JSON.toJSONString(mainConfig));
            baseConfigService.removeProperty(name);
        } catch (Exception e) {
            log.error("HadesConfigService#deleteConfig fail:{}", Throwables.getStackTraceAsString(e));
        }
    }
}
