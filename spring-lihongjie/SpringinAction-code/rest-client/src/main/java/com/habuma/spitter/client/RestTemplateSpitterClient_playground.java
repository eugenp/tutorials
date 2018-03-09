package com.habuma.spitter.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.habuma.spitter.domain.Spitter;
import com.habuma.spitter.domain.Spittle;

public class RestTemplateSpitterClient_playground implements SpitterClient {
  private RestTemplate restTemplate;
  
  public Spittle[] retrieveSpittlesForSpitter(String username) {
    ResponseEntity<Spittle[]> response = 
      new RestTemplate().getForEntity(
        "http://localhost:8080/Spitter/spitters/{spitter}/spittles", 
        Spittle[].class, username);
    
    return response.getBody();
  }

  public String postSpitter(Spitter spitter) {
    // TODO Auto-generated method stub
    return null;
  }
  
  public void updateSpittle(Spittle spittle) throws SpitterException {
      restTemplate.put("http://localhost:8080/Spitter/spittles/{id}", 
                       spittle,  spittle.getId());
  }
  
  public void deleteSpittle(long id) {
    try {
      restTemplate.delete(new URI("http://localhost:8080/Spitter/spittles/54"));
    } catch (URISyntaxException wontHappen) { }
  }
  
  public static void main(String[] args) {
    RestTemplate restTemplate = new RestTemplate();
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("foo", 1234);
    map.put("bar", Collections.singletonMap("baz", "booger"));
    Thing thing = new Thing();
    thing.setFoo("Whazzup");
    thing.setBar(1234L);
    thing.setBaz(true);
    MultiValueMap<String, Object> mvMap = new LinkedMultiValueMap<String, Object>();
    mvMap.add("xyz", "123");
    mvMap.add("abc", "AAA");
    restTemplate.postForObject("http://{host}/index.html", thing, String.class, "localhost:8080");
  }
}
