/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.blazartech.sqsproderdemo;

import java.util.Map;

/**
 *
 * @author aar1069
 */
public interface SqsMessageProducer {

    <T> void send(T message, Map<String, Object> headers);
    
}
