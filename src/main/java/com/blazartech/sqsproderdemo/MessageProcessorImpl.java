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
import org.springframework.stereotype.Component;

/**
 *
 * @author aar1069
 */
@Component
@Slf4j
public class MessageProcessorImpl implements MessageProcessor {

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${demo.doFail:false}")
    private boolean doFail;

    @Override
    public void processMessage(String message) {
        try {
            log.info("received message " + message);

            DemoMessage demoMessage;

            if (message.contains("TopicArn")) {
                log.info("message is a notification");
                SNSNotification notification = objectMapper.readValue(message, SNSNotification.class);
                log.info("notification = " + notification);
                demoMessage = objectMapper.readValue(notification.getMessage(), DemoMessage.class);
            } else {
                log.info("message is a plain message");
                demoMessage = objectMapper.readValue(message, DemoMessage.class);
            }

            log.info("name = " + demoMessage.name());

            if (doFail && demoMessage.doFail()) {
                throw new RuntimeException("intentional fail as requested");
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException("error processing message: " + e.getMessage(), e);
        }
    }

}
