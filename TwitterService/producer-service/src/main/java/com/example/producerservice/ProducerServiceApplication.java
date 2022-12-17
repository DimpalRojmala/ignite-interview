package com.example.producerservice;

import com.example.producerservice.controller.ProducerController;
import com.example.producerservice.entity.TweetBean;
import com.example.producerservice.service.HashtagReaderService;
import com.example.producerservice.twitterapis.TwitterAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableScheduling
public class ProducerServiceApplication {

    @Autowired
    TwitterAPI twitterAPI;

    @Autowired
    ProducerController producerController;

    public static void main(String[] args) {
        SpringApplication.run(ProducerServiceApplication.class, args);
    }

    @Scheduled(fixedDelay = 10000, initialDelay = 10000)
    public void scheduleFixedRateWithInitialDelayTask() {

        long now = System.currentTimeMillis() / 1000;
        System.out.println(
                "Fixed rate task with one second initial delay - " + now);

        HashMap<String, List<String>> hashtagGenreList =
                HashtagReaderService.getHashtagGenreList();

		// As of now hardcoding token
        String bearerToken = "Bearer dkhdfhfskfksskfhskfksbksd";

		for (Map.Entry entry : hashtagGenreList.entrySet()) {
            try {
                String hashTag = (String) entry.getKey();
                //call api to get actual tweet
                //String tweets =TwitterAPI.search(hashTag + "#"+entry.getValue(),bearerToken);

				//for now hardcoded tweet message
				String tweets = "Tweet message for ";

                //get Users from file who are subscribed for this hashtag
                List<String> userSubscription = Files.readAllLines(Path.of("user-subscriber-list.csv"));
                TweetBean tweetBean = new TweetBean();

                int tweetId = (int) (Math.random() * (2000 - 1000 + 1) + 1000);
                tweetBean.setTweetId(tweetId + "");
                tweetBean.setMessage(tweets + "_" + tweetId);
                tweetBean.setHashtag(hashTag);
                ArrayList<String> userList = new ArrayList<>();
                for (String user : userSubscription) {
                    if (user.contains(hashTag)) {
                        userList.add(user.split(":")[1]);
                    }
                }
                tweetBean.setUserList(userList);
                if (!userList.isEmpty()) {
                    producerController.publishMessage(tweetBean);
                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
			/*catch (URISyntaxException e) {
				throw new RuntimeException(e);
			}*/

        }
    }

	/*public  void publishMessageToSubscriber(String message) throws IOException {
		HttpClient httpClient = HttpClients.custom()
				.setDefaultRequestConfig(RequestConfig.custom()
						.setCookieSpec(CookieSpecs.STANDARD).build())
				.build();

		URIBuilder uriBuilder = null;
		HttpGet httpGet;
		try {
			uriBuilder = new URIBuilder("http://localhost:8080/api/publish/"+message);
			 httpGet = new HttpGet(uriBuilder.build());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}


		httpGet.setHeader("Authorization", String.format("Bearer %s", "bearerToken"));
		httpGet.setHeader("Content-Type", "application/json");

		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();

		if (null != entity) {
			String responseMsg = EntityUtils.toString(entity, "UTF-8");
		}


	}*/
}
