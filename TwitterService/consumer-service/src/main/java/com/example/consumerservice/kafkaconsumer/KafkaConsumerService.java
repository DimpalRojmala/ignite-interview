package com.example.consumerservice.kafkaconsumer;

import com.example.consumerservice.entity.TweetBean;
import com.example.consumerservice.repository.ConsumerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @Autowired
    ConsumerRepository consumerRepository;

    @KafkaListener(topics = "NewTopic2", groupId = "group_id")
    public void listen(String message) {
        System.out.println(
                "You have a new message: "
                        + message);
        ObjectMapper mapper = new ObjectMapper();
        TweetBean tweetBean;
        try {
            tweetBean = mapper.readValue(message, TweetBean.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        consumerRepository.save(tweetBean);

    }

    public java.util.List<TweetBean> getAllTweets() {
        return consumerRepository.findAll();
    }

}


