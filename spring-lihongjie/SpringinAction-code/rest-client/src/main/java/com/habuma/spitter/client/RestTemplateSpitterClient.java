package com.habuma.spitter.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.habuma.spitter.domain.Spitter;
import com.habuma.spitter.domain.Spittle;

public class RestTemplateSpitterClient implements SpitterClient {
  //<start id="retrieveSpittlesForSpitter_TheRestTemplateWay"/> 
  public Spittle[] retrieveSpittlesForSpitter(String username) {
    return new RestTemplate().getForObject(
       "http://localhost:8080/Spitter/spitters/{spitter}/spittles", 
       Spittle[].class, username);
  }
  //<end id="retrieveSpittlesForSpitter_TheRestTemplateWay"/>
  
  public Spittle[] retrieveSpittlesForSpitter2(String username) {
    ResponseEntity<Spittle[]> response = new RestTemplate().getForEntity(
        "http://localhost:8080/Spitter/spitters/{spitter}/spittles", 
        Spittle[].class, username);
    
    if(response.getStatusCode() == HttpStatus.NOT_MODIFIED) {
      throw new NotModifiedException();
    }
    
    return response.getBody();
  }

  public Spitter postSpitterForObject(Spitter spitter) { 
    RestTemplate rest = new RestTemplate();
    return rest.postForObject("http://localhost:8080/Spitter/spitters", 
            spitter, Spitter.class);
  }
  
  public String postSpitter(Spitter spitter) { 
    RestTemplate rest = new RestTemplate();
    return rest.postForLocation("http://localhost:8080/Spitter/spitters", 
              spitter, Spitter.class).toString();
  }
  
  public Spitter newSpitter(Spitter spitter) {
    RestTemplate rest = new RestTemplate();
    
    MultiValueMap<String, String> headers = 
        new LinkedMultiValueMap<String, String>();
    headers.add("Accept", "application/json");
    
    HttpEntity<Object> entity = new HttpEntity<Object>(spitter, headers);
    
    ResponseEntity<Spitter> response = rest.exchange(
            "http://localhost:8080/Spitter/spitters", 
            HttpMethod.POST, entity, Spitter.class);

    return response.getBody();
  }
  
  public Spitter getSpitter(String username) {
    RestTemplate rest = new RestTemplate();
    
    MultiValueMap<String, String> headers = 
        new LinkedMultiValueMap<String, String>();
    headers.add("Accept", "application/json");
    
    HttpEntity<Object> entity = new HttpEntity<Object>(headers);
    
    ResponseEntity<Spitter> response = rest.exchange(
            "http://localhost:8080/Spitter/spitters/{spitter}", 
            HttpMethod.GET, entity, Spitter.class, username);
    
    return response.getBody();
  }
  
  public static void main(String[] args) {
    RestTemplate rest = new RestTemplate();
    ResponseEntity<String> response = rest.getForEntity("http://localhost:8080/Spitter/spitters", String.class);
    System.out.println(response.getBody());
    
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
    headers.add("Accept", "text/xml");
    HttpEntity<Object> request = new HttpEntity<Object>(headers );
    ResponseEntity<String> entity = rest.exchange("http://localhost:8080/Spitter/spitters", HttpMethod.GET, request, String.class);
    System.out.println(entity.getBody());
    System.out.println(entity.getHeaders().getAccept());
  }
}
