package com.habuma.spitter.client;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.codehaus.jackson.map.ObjectMapper;

import com.habuma.spitter.domain.Spitter;
import com.habuma.spitter.domain.Spittle;

public class SpitterClientTheHardWay implements SpitterClient {
  //<start id="retrieveSpittlesForSpitter_TheHardWay"/> 
  public Spittle[] retrieveSpittlesForSpitter(String username) {
    try {
      HttpClient httpClient = new DefaultHttpClient();//<co id="co_createHttpClient"/> 
      
      String spittleUrl = "http://localhost:8080/Spitter/spitters/" + 
                          username + "/spittles";//<co id="co_constructUrl"/>
      
      HttpGet getRequest = new HttpGet(spittleUrl);//<co id="co_createRequest"/>      
      getRequest.setHeader(
              new BasicHeader("Accept", "application/json"));
      
      HttpResponse response = httpClient.execute(getRequest);//<co id="co_executeRequest"/>
      
      HttpEntity entity = response.getEntity();//<co id="co_parseResult"/>
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(entity.getContent(), Spittle[].class);
    } catch (IOException e) {
      throw new SpitterClientException("Unable to retrieve Spittles", e);
    }
  }
  //<end id="retrieveSpittlesForSpitter_TheHardWay"/>
  
  public String postSpitter(Spitter spitter) {
    // TODO Auto-generated method stub
    return null;
  }
}
