package com.baeldung.maxhttpheadersize.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
// Start MaxHttpHeaderSizeController Spring Boot App(MaxHttpHeaderSizeApplication) first
public class MaxHttpHeaderSizeControllerLiveTest {

    @Test(expected = HttpClientErrorException.class)
    public void givenTokenWithGreaterThan8KBLegth_whenSendGetRequest_thenThrowsBadRequest() throws Exception {
        final String url = "http://localhost:8080/request-header-test";
        HttpHeaders headers = new HttpHeaders();
        headers.set("token", readRandomStringFromFile());

        HttpEntity entity = new HttpEntity(headers);
        final ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, entity, String.class);
    }

    static String readRandomStringFromFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/randomSringForheader.txt"));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        reader.close();
        String content = stringBuilder.toString();
        return content;
    }

}
