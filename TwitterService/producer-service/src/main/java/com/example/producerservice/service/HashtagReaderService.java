package com.example.producerservice.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HashtagReaderService {

    static HashMap<String, List<String>> hashtagGenreList = new HashMap<>();

    @PostConstruct
    public static void init() {
        Path path = Path.of("hashtag-genrelist.csv");
        try {
            List<String> hashtagGenreListStr = Files.readAllLines(path);

            for (String line : hashtagGenreListStr) {
                String[] split = line.split(",");
                String hashtag = split[0].trim();

                List<String> genreList = Arrays.asList(split).stream().skip(1).collect(Collectors.toList());

                hashtagGenreList.put(hashtag, genreList);
            }
            hashtagGenreList.entrySet()
                    .forEach(i -> System.out.println("id : " + i.getKey() + " val : " + i.getValue()));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static HashMap<String, List<String>> getHashtagGenreList() {
        return hashtagGenreList;
    }
}

