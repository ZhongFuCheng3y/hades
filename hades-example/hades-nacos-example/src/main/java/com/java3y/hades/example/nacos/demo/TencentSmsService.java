package com.java3y.hades.example.nacos.demo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 3y
 */
@Slf4j
public class TencentSmsService implements SendSmsService {

    @Override
    public void send() {
        log.error("aa");

        System.out.println("send success");
    }
}
