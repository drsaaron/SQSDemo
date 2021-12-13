/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.sqsproderdemo.jms;

import com.blazartech.sqsproderdemo.DemoMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author aar1069
 */
@Component
@Slf4j
@Profile("jms")
public class DemoMessageJMSListener implements MessageListener {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @JmsListener(destination = "${demo.queueName}")
    public void onMessage(Message msg) {
        if (msg instanceof TextMessage textMessage) {
            try {
                String message = textMessage.getText();
                log.info("received message " + message);
                DemoMessage demoMessage = objectMapper.readValue(message, DemoMessage.class);

                log.info("name = " + demoMessage.name());

                if (demoMessage.doFail()) {
                    throw new RuntimeException("intentional fail as requested");
                }

            } catch (JMSException | JsonProcessingException e) {
                throw new RuntimeException("error processing message: " + e.getMessage(), e);
            }
        }
    }

}
