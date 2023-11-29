/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.sqsproderdemo.jms;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import jakarta.jms.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.util.ErrorHandler;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

/**
 * configure JMS
 * @author aar1069
 */
@EnableJms
@Configuration
@Slf4j
@Profile("jms")
public class SQSJMSConfiguration {

    @Value("${cloud.aws.region:us-east-2}")
    private String region;
    
    @Autowired
    private ErrorHandler errorHandler;
    
    @Autowired
    private AwsCredentialsProvider awsCredentialsProvider;
    
    @Bean
    public SQSConnectionFactory sqsConnectionFactory() {
        Region awsRegion = Region.of(region);
        SqsClient client = SqsClient.builder()
                .credentialsProvider(awsCredentialsProvider)
                .region(awsRegion)
                .build();
        SQSConnectionFactory connectionFactory = new SQSConnectionFactory(
                new ProviderConfiguration(),
                client
        );
        return connectionFactory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(sqsConnectionFactory());
        factory.setDestinationResolver(new DynamicDestinationResolver());
        factory.setConcurrency("3-10");
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        factory.setErrorHandler(errorHandler);
        return factory;
    }

    @Bean
    public JmsTemplate defaultJmsTemplate() {
        JmsTemplate template = new JmsTemplate(sqsConnectionFactory());
        return template;
    }
}
