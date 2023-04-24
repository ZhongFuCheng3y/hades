package com.java3y.hades.controller;

import com.alibaba.fastjson2.JSON;
import com.google.common.base.Throwables;
import com.java3y.hades.annotation.HadesResult;
import com.java3y.hades.core.domain.MainConfig;
import com.java3y.hades.core.service.bootstrap.HadesConfig;
import com.java3y.hades.core.service.config.HadesConfigProperties;
import com.java3y.hades.domain.HadesParam;
import com.java3y.hades.enums.RespStatusEnum;
import com.java3y.hades.vo.HadesConfigListVo;
import com.java3y.hades.vo.basic.BasicResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置接口
 *
 * @author 3y
 */
@RestController
@Slf4j
@HadesResult
public class ConfigController {

    @Autowired
    private HadesConfig configService;
    @Autowired
    protected HadesConfigProperties configProperties;

    @RequestMapping("/config/add")
    private BasicResultVO addConfig(@RequestBody HadesParam hadesParam) {
        try {
            MainConfig mainConfig = JSON.parseObject(configService.getConfigValueByName(configProperties.getConfigName()), MainConfig.class);
            if (mainConfig.getInstanceNames().contains(hadesParam.getName())) {
                return BasicResultVO.fail(RespStatusEnum.FAIL, "已存在相同名的脚本，禁止添加");
            }
            mainConfig.getInstanceNames().add(hadesParam.getName());
            mainConfig.setUpdateTime(System.currentTimeMillis());
            configService.addOrUpdateProperty(configProperties.getConfigName(), JSON.toJSONString(mainConfig));
            configService.addOrUpdateProperty(hadesParam.getName(), hadesParam.getRuleLogicCode());
            return BasicResultVO.success();
        } catch (Exception e) {
            log.error("ConfigController#addConfig fail:{}", Throwables.getStackTraceAsString(e));
        }
        return BasicResultVO.fail();
    }

    @RequestMapping("/config/get")
    private List<HadesConfigListVo> getAllConfig(String keywords) {

        List<HadesConfigListVo> result = new ArrayList<>();
        try {
            MainConfig mainConfig = JSON.parseObject(configService.getConfigValueByName(configProperties.getConfigName()), MainConfig.class);
            for (String instanceName : mainConfig.getInstanceNames()) {
                if (StringUtils.hasText(keywords) && !instanceName.contains(keywords)) {
                    continue;
                }
                HadesConfigListVo hadesConfigListVo = HadesConfigListVo.builder().name(instanceName).ruleLogicCode(configService.getConfigValueByName(instanceName)).build();
                result.add(hadesConfigListVo);
            }
        } catch (Exception e) {
            log.error("ConfigController#getAllConfig fail:{}", Throwables.getStackTraceAsString(e));
        }
        return result;
    }

    @RequestMapping("/config/delete")
    private BasicResultVO deleteConfig(String name) {
        try {
            MainConfig mainConfig = JSON.parseObject(configService.getConfigValueByName(configProperties.getConfigName()), MainConfig.class);
            mainConfig.getInstanceNames().remove(name);
            configService.addOrUpdateProperty(configProperties.getConfigName(), JSON.toJSONString(mainConfig));
            return BasicResultVO.success();
        } catch (Exception e) {
            log.error("ConfigController#deleteConfig fail:{}", Throwables.getStackTraceAsString(e));
        }
        return BasicResultVO.fail();
    }
}
