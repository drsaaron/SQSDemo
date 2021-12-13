/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.sqsproderdemo;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 *
 * @author aar1069
 */
@Slf4j
@Component
public class SendMessageCommandLineRunner implements CommandLineRunner {

    @Autowired
    private SqsMessageProducer messageProducer;
    
    @Override
    public void run(String... args) throws Exception {
        int count = 1;
        for (String name : args) {
            DemoMessage message = new DemoMessage(count, name, count++ % 4 == 0);
            
            Map<String, Object> headers = Map.of("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            
            log.info("sending message " + message);
            messageProducer.send(message, headers);
        }
    }
    
}
