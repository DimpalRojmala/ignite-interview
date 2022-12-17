package com.example.consumerservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TWEET_TBL")
public class TweetBean {

    @Id
    String tweetId;
    String message;
    String hashtag;
    List<String> userList;

}
