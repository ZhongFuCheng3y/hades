package com.java3y.hades;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 3y
 * apollo 启动类
 */
@SpringBootApplication
public class ApolloExampleApplication {

    public static void main(String[] args) {
        // 我的apollo是docker部署，跳过meta服务发现
        System.setProperty("apollo.config-service", "http://austin.apollo.config:5001");
        SpringApplication.run(ApolloExampleApplication.class, args);
    }
}
