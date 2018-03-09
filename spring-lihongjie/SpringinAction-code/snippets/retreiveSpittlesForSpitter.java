//<start id="retrieveSpittlesForSpitter_MappedParams"/> 
public Spittle[] retrieveSpittlesForSpitter(String username) {
  Map<String, String> params = new HashMap<String, String>();
  params.put("spitter", username);
  return new RestTemplate().getForObject(
     "http://localhost:8080/Spitter/spitters/{spitter}/spittles", 
     Spittle[].class, params);
}
//<end id="retrieveSpittlesForSpitter_MappedParams"/> 



//<start id="retrieveSpittlesForSpitter_HttpStatusCodes"/> 
public Spittle[] retrieveSpittlesForSpitter(String username) {
  ResponseEntity<Spittle[]> response = new RestTemplate().getForEntity(
      "http://localhost:8080/Spitter/spitters/{spitter}/spittles", 
      Spittle[].class, username);
  
  if(response.getStatusCode() == HttpStatus.NOT_MODIFIED) {
    throw new NotModifiedException();
  }
  
  return response.getBody();
}
//<end id="retrieveSpittlesForSpitter_HttpStatusCodes"/> 