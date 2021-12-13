/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.sqsproderdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 *
 * @author aar1069
 */
@Component
@Slf4j
@Profile("sqs")
public class DemoMessageListener {
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Value("${demo.doFail:false}")
    private boolean doFail;
    
    @SqsListener(value = "${demo.queueName}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void processMessage(String message) {
        try {
            log.info("received message " + message);
            DemoMessage demoMessage = objectMapper.readValue(message, DemoMessage.class);
            
            log.info("name = " + demoMessage.name());
            
            if (doFail && demoMessage.doFail()) {
                throw new RuntimeException("intentional fail as requested");
            } 
            
        } catch (JsonProcessingException e) {
            throw new RuntimeException("error processing message: " + e.getMessage(), e);
        }
    }
    
}
