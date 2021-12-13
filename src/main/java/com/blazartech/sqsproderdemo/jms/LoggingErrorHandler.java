/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.sqsproderdemo.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

/**
 *
 * @author aar1069
 */
@Component
@Slf4j
public class LoggingErrorHandler implements ErrorHandler {

    @Override
    public void handleError(Throwable t) {
        log.error("error handling message: " + t.getMessage(), t);
    }
    
}
