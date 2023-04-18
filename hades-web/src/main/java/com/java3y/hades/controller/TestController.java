package com.java3y.hades.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 *
 * @author 3y
 */
@RestController
@Slf4j
public class TestController {
    @RequestMapping("/test")
    private String test() {
        return "success";
    }
}
