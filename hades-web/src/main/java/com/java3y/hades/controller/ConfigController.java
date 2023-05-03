package com.java3y.hades.controller;

import com.alibaba.fastjson2.JSON;
import com.java3y.hades.annotation.HadesAspect;
import com.java3y.hades.annotation.HadesResult;
import com.java3y.hades.core.domain.MainConfig;
import com.java3y.hades.core.service.bootstrap.HadesBaseConfig;
import com.java3y.hades.core.service.config.HadesConfigProperties;
import com.java3y.hades.domain.HadesParam;
import com.java3y.hades.enums.RespStatusEnum;
import com.java3y.hades.service.HadesConfigService;
import com.java3y.hades.vo.HadesConfigListVo;
import com.java3y.hades.vo.basic.BasicResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 配置接口
 *
 * @author 3y
 */
@RestController
@Slf4j
@HadesResult
@HadesAspect
public class ConfigController {

    @Autowired
    private HadesBaseConfig baseConfigService;
    @Autowired
    private HadesConfigService configService;
    @Autowired
    protected HadesConfigProperties configProperties;

    @RequestMapping("/config/add")
    private BasicResultVO addConfig(@RequestBody HadesParam hadesParam) {
        MainConfig mainConfig = JSON.parseObject(baseConfigService.getConfigValueByName(configProperties.getConfigName()), MainConfig.class);
        if (mainConfig.getInstanceNames().contains(hadesParam.getName())) {
            return BasicResultVO.fail(RespStatusEnum.FAIL, "已存在相同名的脚本，禁止添加");
        }
        configService.addConfig(hadesParam);
        return BasicResultVO.success();
    }

    @RequestMapping("/config/get")
    private List<HadesConfigListVo> getAllConfig(String keywords) {
        return configService.getAllConfig(keywords);
    }

    @RequestMapping("/config/delete")
    private BasicResultVO deleteConfig(String name) {
        configService.deleteConfig(name);
        return BasicResultVO.success();
    }

}
