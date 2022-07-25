/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.sqsproderdemo;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 *
 * @author aar1069
 */
@Component
@Slf4j
public class SendNotificationCommandLineRunner implements CommandLineRunner {

    @Autowired
    @Qualifier("snsMessageProducer")
    private AwsMessageProducer messageProducer;

    @Override
    public void run(String... args) throws Exception {
        DemoMessage message = new DemoMessage(15, "temp SNS", false);
        log.info("sending notification " + message);
        Map<String, Object> headers = Map.of("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        messageProducer.send(message, headers);
    }
    
}
