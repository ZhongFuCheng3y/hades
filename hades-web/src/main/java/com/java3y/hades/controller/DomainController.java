package com.java3y.hades.controller;


import com.alibaba.fastjson2.JSON;
import com.java3y.hades.annotation.HadesResult;
import com.java3y.hades.core.constant.CommonConstant;
import com.java3y.hades.service.HadesConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 规则域管理接口
 *
 * @author 3y
 */
@RestController("domain")
@Slf4j
@HadesResult
public class DomainController {

    @Autowired
    private HadesConfigService configService;

    public static final String HADES_DOMAIN_KEY = "hades_domain";

    /**
     * 获取所有的规则域
     *
     * @return
     */
    @RequestMapping("/getAll")
    private List<String> getAllDomain() {
        return JSON.parseArray(configService.getProperty(HADES_DOMAIN_KEY, CommonConstant.EMPTY_VALUE_JSON_ARRAY), String.class);
    }

    /**
     * 获取所有的规则域
     *
     * @return
     */
    @RequestMapping("/add")
    private void addDomain(String domain) {
        List<String> list = JSON.parseArray(configService.getProperty(HADES_DOMAIN_KEY, CommonConstant.EMPTY_VALUE_JSON_ARRAY), String.class);
        list.add(domain);
        List<String> collect = list.stream().distinct().collect(Collectors.toList());
    }
}
