package com.example.producerservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TweetBean {

    String tweetId;
    String message;
    String hashtag;
    List<String> userList;

}
