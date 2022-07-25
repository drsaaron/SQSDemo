/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.sqsproderdemo.sns;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author aar1069
 */
@Configuration
public class AWSSnsConfiguration {

    @Value("${cloud.aws.region:us-east-2}")
    private String region;
    
    @Autowired
    private AWSCredentialsProvider credentialsProvider;
    
    @Bean
    public AmazonSNS amazonSNS() {
        
        return AmazonSNSAsyncClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(region)
                .build();
    }
    
    @Bean
    public NotificationMessagingTemplate notificationMessagingTemplate() {
        return new NotificationMessagingTemplate(amazonSNS());
    }
}
