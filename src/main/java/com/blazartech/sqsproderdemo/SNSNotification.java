/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.sqsproderdemo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author aar1069
 */
@Data
public class SNSNotification implements Serializable {
    
    @JsonProperty("Type") private String type;
    @JsonProperty("MessageId") private String messageId;
    @JsonProperty("Message") private String message;
    @JsonProperty("Subject") private String subject;
}
