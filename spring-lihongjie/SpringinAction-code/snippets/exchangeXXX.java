//<start id="exchange"/> 
<T> ResponseEntity<T> exchange(URI url, HttpMethod method, 
        HttpEntity<?> requestEntity, Class<T> responseType) 
        throws RestClientException;

<T> ResponseEntity<T> exchange(String url, HttpMethod method, 
        HttpEntity<?> requestEntity, Class<T> responseType, 
        Object... uriVariables) throws RestClientException;

<T> ResponseEntity<T> exchange(String url, HttpMethod method, 
        HttpEntity<?> requestEntity, Class<T> responseType, 
        Map<String, ?> uriVariables) throws RestClientException;
//<end id="exchange"/> 

//<start id="exchange_get"/> 
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
//<end id="exchange_get"/> 




//<start id="entity_spitter"/> 
HttpEntity<Object> entity = new HttpEntity<Object>(spitter, headers);
//<end id="entity_spitter"/> 
