/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.sqsproderdemo.jms;

import com.blazartech.sqsproderdemo.SqsMessageProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author aar1069
 */
@Component
@Slf4j
@Profile("jms")
public class SqsJMSMessageProducerImpl implements SqsMessageProducer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JmsTemplate queueMessagingTemplate;

    @Value("${demo.queueName}")
    private String queueName;

    @Override
    public <T> void send(T message, Map<String, Object> headers) {
        try {
            if (message == null) {
                log.warn("SQS producer cannot produce null message");
                return;
            }

            String jsonMessage = objectMapper.writeValueAsString(message);

            log.info("Message: {}", jsonMessage);
            log.info("Queue name {} ", queueName);

            queueMessagingTemplate.convertAndSend(queueName, jsonMessage);
        } catch (JmsException | JsonProcessingException e) {
            throw new RuntimeException("error sending message: " + e.getMessage(), e);
        }
    }

}
