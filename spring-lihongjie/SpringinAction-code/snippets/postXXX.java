//<start id="postForLocation"/> 
URI postForLocation(String url, Object request, Object... uriVariables) 
        throws RestClientException;

URI postForLocation(
        String url, Object request, Map<String, ?> uriVariables)  
        throws RestClientException;

URI postForLocation(URI url, Object request) throws RestClientException;
//<end id="postForLocation" /> 

//<start id="postForObject"/> 
<T> T postForObject(URI url, Object request, Class<T> responseType) 
        throws RestClientException;

<T> T postForObject(String url, Object request, Class<T> responseType, 
        Object... uriVariables) throws RestClientException;

<T> T postForObject(String url, Object request, Class<T> responseType, 
        Map<String, ?> uriVariables) throws RestClientException;
//<end id="postForObject"/> 

//<start id="postForEntity"/> 
<T> ResponseEntity<T> postForEntity(URI url, Object request, 
        Class<T> responseType) throws RestClientException;

<T> ResponseEntity<T> postForEntity(String url, Object request, 
        Class<T> responseType, Object... uriVariables) 
        throws RestClientException;

<T> ResponseEntity<T> postForEntity(String url, Object request, 
        Class<T> responseType, Map<String, ?> uriVariables)
		throws RestClientException;
//<end id="postForEntity"/> 


//<start id="postForObject_object"/> 
public Spitter postSpitterForObject(Spitter spitter) { 
  RestTemplate rest = new RestTemplate();
  return rest.postForObject("http://localhost:8080/Spitter/spitters", 
          spitter, Spitter.class);
}
//<end id="postForObject_object"/> 

//<start id="postForEntity_object"/> 
RestTemplate rest = new RestTemplate();
ResponseEntity<Spitter> response = rest.postForEntity(
    "http://localhost:8080/Spitter/spitters", spitter, Spitter.class);

Spitter spitter = response.getBody();
URI url = response.getHeaders().getLocation();
//<end id="postForEntity_object"/> 


//<start id="postForLocation_object"/> 
public String postSpitter(Spitter spitter) { 
  RestTemplate rest = new RestTemplate();
  return rest.postForLocation("http://localhost:8080/Spitter/spitters", 
            spitter).toString();
}
//<end id="postForLocation_object"/> 