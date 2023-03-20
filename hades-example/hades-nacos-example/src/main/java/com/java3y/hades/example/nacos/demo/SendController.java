package com.java3y.hades.example.nacos.demo;

import com.java3y.hades.core.HadesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 3y
 * test
 */
@RestController
public class SendController {

    @Autowired
    private HadesClient hadesClient;

    @RequestMapping("/send")
    private void send() {
        SendSmsService sendSmsService = hadesClient.getInterfaceByName("TencentSmsService");
        sendSmsService.send();
    }

}
