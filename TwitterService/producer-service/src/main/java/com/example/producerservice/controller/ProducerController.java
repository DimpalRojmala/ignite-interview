package com.example.producerservice.controller;


import com.example.producerservice.entity.TweetBean;
import com.example.producerservice.exception.CustomException;
import com.example.producerservice.exception.ProducerExceptionHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.kafka.common.errors.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api")
public class ProducerController {

    private static final String TOPIC = "NewTopic2";
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/test")
    public String test() {
        return "test successful";
    }

    @PostMapping("/publish")
    public String publishMessage(@RequestBody TweetBean message) {


        // Sending the message
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json;
        try {
            json = ow.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Publishing message : " + json);
        kafkaTemplate.send(TOPIC, json);

        return "Published Successfully";
    }

    @GetMapping("/subscribe-user")
    public String subscribeUser(@RequestParam("userId") final String userId, @RequestParam("hashTag") final String hashTag) {
        Path path = Path.of("user-subscriber-list.csv");

        try {
            if (userId != "" && hashTag != "") {
                String content = hashTag + ":" + userId;
                //Files.write(path, content.getBytes(StandardCharsets.UTF_8));
                Files.write(path, content.getBytes(StandardCharsets.UTF_8));
            } else {
                throw new CustomException("user id or hashtag null");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "subscribed successfully";
    }
}