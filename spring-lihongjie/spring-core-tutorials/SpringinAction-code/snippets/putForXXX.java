//<start id="putXXX"/> 
void put(URI url, Object request) throws RestClientException;

void put(String url, Object request, Object... uriVariables) 
        throws RestClientException;

void put(String url, Object request, Map<String, ?> uriVariables) 
        throws RestClientException;
//<end id="putXXX"/> 


//<start id="updateSpittle_URI"/> 
public void updateSpittle(Spittle spittle) throws SpitterException {
  try {
    String url = "http://localhost:8080/Spitter/spittles/" + spittle.getId();
    new RestTemplate().put(new URI(url), spittle);
  } catch (URISyntaxException e) {
    throw new SpitterUpdateException("Unable to update Spittle", e);
  }
}
//<end id="updateSpittle_URI"/> 

//<start id="updateSpittle_varags"> 
public void updateSpittle(Spittle spittle) throws SpitterException {
    restTemplate.put("http://localhost:8080/Spitter/spittles/{id}", 
                     spittle,  spittle.getId());
}
//<end id="updateSpittle_varags"> 

//<start id="updateSpittle_map"> 
public void updateSpittle(Spittle spittle) throws SpitterException {
    Map<String, String> params = new HashMap<String, String>();
    params.put("id", spittle.getId());
    restTemplate.put("http://localhost:8080/Spitter/spittles/{id}", 
                     spittle,  params);
}
//<end id="updateSpittle_map"> 
