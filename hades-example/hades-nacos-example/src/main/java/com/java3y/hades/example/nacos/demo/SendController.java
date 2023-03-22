package com.java3y.hades.example.nacos.demo;

import com.java3y.hades.core.HadesClient;
import groovy.lang.GroovyObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 3y
 * test
 */
@RestController
@Slf4j
public class SendController {

    @Autowired
    private HadesClient hadesClient;

    @RequestMapping("/send")
    private void send() {
        SendSmsService sendSmsService = hadesClient.getInterfaceByName("TencentSmsService");
        GroovyObject groovyObject = hadesClient.getGroovyObjectByName("TencentSmsService");
        log.info("groovy object:{}", groovyObject);
        groovyObject.invokeMethod("send", null);
        Object execute = hadesClient.execute("TencentSmsService", "send", null);
        sendSmsService.send();
        System.out.println("result" + execute);

    }

}
