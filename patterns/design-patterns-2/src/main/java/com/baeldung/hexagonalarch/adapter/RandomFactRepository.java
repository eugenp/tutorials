package com.baeldung.hexagonalarch.adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import com.baeldung.hexagonalarch.domain.RandomFact;
import com.baeldung.hexagonalarch.domain.ports.FetchFactsRepository;

public class RandomFactRepository implements FetchFactsRepository {

    @Override
    public RandomFact fetch() throws IOException {
        String url = "http://numbersapi.com/random/trivia";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuffer response = new StringBuffer();

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return new RandomFact(UUID.randomUUID().toString(), response.toString());
    }
}
