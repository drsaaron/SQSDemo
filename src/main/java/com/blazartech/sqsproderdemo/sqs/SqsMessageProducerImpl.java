/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.sqsproderdemo.sqs;

import com.blazartech.sqsproderdemo.SqsMessageProducer;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 *
 * @author aar1069
 */
@Component
@Slf4j
@Profile("sqs")
public class SqsMessageProducerImpl implements SqsMessageProducer {
    
    private final QueueMessagingTemplate queueMessagingTemplate;
    
    @Value("${demo.queueName}")
    private String queueName;

    public SqsMessageProducerImpl(QueueMessagingTemplate queueMessagingTemplate) {
        this.queueMessagingTemplate = queueMessagingTemplate;
    }
    
    @Override
    public <T> void send(T message, Map<String, Object> headers) {
        if (message == null) {
            log.warn("SQS producer cannot produce null message");
            return;
        }
        
        log.info("Message: {}" + message);
        log.info("Queue name {} " + queueName);
        
        queueMessagingTemplate.convertAndSend(queueName, message, headers);
    }
}
