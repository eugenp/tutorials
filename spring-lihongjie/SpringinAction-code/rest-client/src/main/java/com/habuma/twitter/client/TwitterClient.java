package com.habuma.twitter.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.client.RestTemplate;

public class TwitterClient {
  public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("twitter-client.xml");
    
    RestTemplate rest = context.getBean(RestTemplate.class);
    
    String result = rest.getForObject("http://api.twitter.com/users/show/springinaction.json", String.class);
    System.out.println(result);
  }
}
