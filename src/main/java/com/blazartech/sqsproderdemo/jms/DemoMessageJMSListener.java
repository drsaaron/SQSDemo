/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.sqsproderdemo.jms;

import com.blazartech.sqsproderdemo.MessageProcessor;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
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
    private MessageProcessor messageProcessor;

    @Override
    @JmsListener(destination = "${demo.queueName}")
    public void onMessage(Message msg) {
        if (msg instanceof TextMessage textMessage) {
            try {
                String message = textMessage.getText();
                messageProcessor.processMessage(message);
            } catch (JMSException e) {
                throw new RuntimeException("error processing message: " + e.getMessage(), e);
            }
        }
    }

}
