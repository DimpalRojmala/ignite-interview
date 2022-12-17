package com.example.consumerservice.repository;

import com.example.consumerservice.entity.TweetBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerRepository extends JpaRepository<TweetBean, String> {

}
