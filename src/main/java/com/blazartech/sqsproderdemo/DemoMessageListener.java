/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.sqsproderdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

/**
 *
 * @author aar1069
 */
@Component
@Slf4j
public class DemoMessageListener {
    
    private static final ObjectMapper OBJECT_MAPPER = Jackson2ObjectMapperBuilder.json().build();
    
    @SqsListener(value = "${demo.queueName}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void processMessage(String message) {
        try {
            log.info("received message " + message);
            DemoMessage demoMessage = OBJECT_MAPPER.readValue(message, DemoMessage.class);
            
            log.info("name = " + demoMessage.name());
            
            if (demoMessage.doFail()) {
                throw new RuntimeException("intentional fail as requested");
            } 
            
        } catch (JsonProcessingException e) {
            throw new RuntimeException("error processing message: " + e.getMessage(), e);
        }
    }
    
}
