//<start id="deleteXXX"/> 
void delete(String url, Object... uriVariables) 
        throws RestClientException;

void delete(String url, Map<String, ?> uriVariables) 
        throws RestClientException;

void delete(URI url) throws RestClientException;
//<end id="deleteXXX"/> 

//<start id="delete_URI"/> 
public void deleteSpittle(long id) {
  try {
    restTemplate.delete(
                  new URI("http://localhost:8080/Spitter/spittles/" + id));
  } catch (URISyntaxException wontHappen) { }
}
//<end id="delete_URI"/> 

//<start id="delete_varargs"/> 
public void deleteSpittle(long id) {
  restTemplate.delete("http://localhost:8080/Spitter/spittles/{id}", id));
}
//<end id="delete_varargs"/>