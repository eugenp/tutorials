<T> T getForObject(String url, Class<T> responseType, Object... uriVariables) 
    throws RestClientException;

<T> T getForObject(
    String url, Class<T> responseType, Map<String, ?> uriVariables) 
    throws RestClientException;

<T> T getForObject(URI url, Class<T> responseType) throws RestClientException;