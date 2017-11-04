/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.googlehttpclientguide;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

/**
 *
 * @author Hugo
 */
public class DailyMotionExample {

    static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    static final JsonFactory JSON_FACTORY = new JacksonFactory();
    //static final JsonFactory JSON_FACTORY = new GsonFactory();

    private static void run() throws Exception {
        HttpRequestFactory requestFactory
                = HTTP_TRANSPORT.createRequestFactory(
                        new HttpRequestInitializer() {

                    public void initialize(HttpRequest request) {
                        request.setParser(new JsonObjectParser(JSON_FACTORY));
                    }
                });
        DailyMotionUrl url = new DailyMotionUrl("https://api.dailymotion.com/videos/");
        url.fields = "id,tags,title,url";
        HttpRequest request = requestFactory.buildGetRequest(url);
        VideoFeed videoFeed = request.execute().parseAs(VideoFeed.class);
        if (videoFeed.list.isEmpty()) {
            System.out.println("No videos found.");
        } else {
            if (videoFeed.hasMore) {
                System.out.print("First ");
            }
            System.out.println(videoFeed.list.size() + " videos found:");
            for (Video video : videoFeed.list) {
                System.out.println();
                System.out.println("-----------------------------------------------");
                System.out.println("ID: " + video.id);
                System.out.println("Title: " + video.title);
                System.out.println("Tags: " + video.tags);
                System.out.println("URL: " + video.url);
            }
        }
    }

    public static void main(String[] args) {
        try {
            try {
                run();
                return;
            } catch (HttpResponseException e) {
                System.err.println(e.getMessage());
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.exit(1);
    }

}
