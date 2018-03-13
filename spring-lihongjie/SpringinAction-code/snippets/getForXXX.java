//<start id="getForObject"/> 
<T> T getForObject(URI url, Class<T> responseType) 
                                                throws RestClientException;

<T> T getForObject(String url, Class<T> responseType, 
                   Object... uriVariables)  throws RestClientException;
        
<T> T getForObject(String url, Class<T> responseType, 
        Map<String, ?> uriVariables) throws RestClientException;        
//<end id="getForObject"/> 


//<start id="getForEntity"/> 
<T> ResponseEntity<T> getForEntity(URI url, Class<T> responseType) 
        throws RestClientException;

<T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, 
        Object... uriVariables) throws RestClientException;
        
<T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, 
        Map<String, ?> uriVariables) throws RestClientException;        
//<end id="getForEntity"/> 

//<start id="getForObject_mapVariables"/> 
public Spittle[] retrieveSpittlesForSpitter(String username) { 
    Map<String, String> urlVariables = new HashMap<String, String();
    urlVariables.put("spitter", username);
    return new RestTemplate().getForObject(
        "http://localhost:8080/Spitter/spitters/{spitter}/spittles", 
        Spittle[].class, urlVariables);
}
//<end id="getForObject_mapVariables"/> 
