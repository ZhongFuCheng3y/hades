package com.java3y.hades.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检查接口
 *
 * @author 3y
 */
@RestController
@Slf4j
public class HealthController {
    @RequestMapping("/health")
    private String health() {
        return "success";
    }
}
