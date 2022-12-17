package com.example.producerservice.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.apache.http.HttpStatus.*;

@ControllerAdvice
public class ProducerExceptionHandler extends Exception {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException() {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "User name or hashtag null");

        return new ResponseEntity<>(body, HttpStatusCode.valueOf(SC_BAD_REQUEST));
    }
}
