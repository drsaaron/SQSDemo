/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.sqsproderdemo.sns;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.stereotype.Component;
import com.blazartech.sqsproderdemo.AwsMessageProducer;

/**
 *
 * @author aar1069
 */
@Component("snsMessageProducer")
@Slf4j
public class SnsMessageProducerImpl implements AwsMessageProducer {
    
    @Autowired
    private NotificationMessagingTemplate queueMessagingTemplate;
    
    @Value("${demo.topicName}")
    private String topicName;

    @Override
    public <T> void send(T message, Map<String, Object> headers) {
        if (message == null) {
            log.warn("SQS producer cannot produce null message");
            return;
        }
        
        log.info("Message: {}" + message);
        log.info("Queue name {} " + topicName);
        
        queueMessagingTemplate.sendNotification(topicName, message, "test");
    }
    
}
