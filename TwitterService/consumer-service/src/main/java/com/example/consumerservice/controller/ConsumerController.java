package com.example.consumerservice.controller;

import com.example.consumerservice.entity.TweetBean;
import com.example.consumerservice.kafkaconsumer.KafkaConsumerService;
import com.example.consumerservice.repository.ConsumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/consumer/api")
public class ConsumerController {

    @Autowired
    KafkaConsumerService kafkaConsumerService;

    @GetMapping("/tweets")
    public List<TweetBean> getAllTweets() {
        return kafkaConsumerService.getAllTweets();
    }

}
