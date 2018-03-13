package exploration;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.habuma.spitter.domain.Spitter;

public class RestExplorationTest {
  @Test
  public void shouldGetSpitter() {
    RestTemplate rest = new RestTemplate();
    
    String spitterId = "habuma";
    
    //<start id="getForObject_getSpitter"/> 
    Spitter spitter = rest.getForObject(
              "http://localhost:8080/Spitter/spitters/{spitter}", 
              Spitter.class, spitterId);
    //<end id="getForObject_getSpitter"/> 
    
    assertExpectedSpitter(spitter);
  }

  @Test
  public void shouldGetSpitterEntity() {
    RestTemplate rest = new RestTemplate();
    
    String spitterId = "habuma";
    
    //<start id="getForObject_getSpitterEntity"/> 
    ResponseEntity<Spitter> response = rest.getForEntity(
              "http://localhost:8080/Spitter/spitters/{spitter}", 
              Spitter.class, spitterId);
    Spitter spitter = response.getBody();
    //<end id="getForObject_getSpitterEntity"/> 
    
    assertExpectedSpitter(spitter);
  }
  
  @Test
  public void shouldExchangeForSpitterEntity() {
    RestTemplate rest = new RestTemplate();
    
    String spitterId = "habuma";
    
    //<start id="getForObject_exchangeForSpitter"/> 
    ResponseEntity<Spitter> response = rest.exchange(
              "http://localhost:8080/Spitter/spitters/{spitter}", 
              HttpMethod.GET, null, Spitter.class, spitterId);
    Spitter spitter = response.getBody();
    //<end id="getForObject_exchangeForSpitter"/> 
    
    assertExpectedSpitter(spitter);    
  }
  
  @Test
  public void shouldExchangeForSpitterEntity_acceptJSON() {
    RestTemplate rest = new RestTemplate();
    
    String spitterId = "habuma";
    
    //<start id="getForObject_buildRequestEntity"/> 
    MultiValueMap<String, String> headers = 
        new LinkedMultiValueMap<String, String>();
    headers.add("Accept", "application/json");
    HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
    //<end id="getForObject_buildRequestEntity"/> 
    
    //<start id="getForObject_exchangeForSpitter_JSON"/> 
    ResponseEntity<Spitter> response = rest.exchange(
              "http://localhost:8080/Spitter/spitters/{spitter}", 
              HttpMethod.GET, requestEntity, Spitter.class, spitterId);
    Spitter spitter = response.getBody();
    //<end id="getForObject_exchangeForSpitter_JSON"/> 
    
    assertExpectedSpitter(spitter);    
  }
  
  private void assertExpectedSpitter(Spitter spitter) {
    assertEquals(1L, spitter.getId().longValue());
    assertEquals("habuma", spitter.getUsername());
    assertEquals("Craig Walls", spitter.getFullName());
    assertEquals("craig@habuma.com", spitter.getEmail());
  }
  

}
