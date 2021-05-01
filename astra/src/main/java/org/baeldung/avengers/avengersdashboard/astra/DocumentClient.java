package org.baeldung.avengers.avengersdashboard.astra;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Repository
public class DocumentClient {
  @Value("https://${ASTRA_DB_ID}-${ASTRA_DB_REGION}.apps.astra.datastax.com/api/rest/v2/namespaces/${ASTRA_DB_KEYSPACE}")
  private String baseUrl;

  @Value("${ASTRA_DB_APPLICATION_TOKEN}")
  private String token;

  @Autowired
  private ObjectMapper objectMapper;

  private RestTemplate restTemplate;

  public DocumentClient() {
    this.restTemplate = new RestTemplate();
    this.restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
  }

  public <T> T getDocument(String collection, String id, Class<T> cls) {
    var uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
      .pathSegment("collections", collection, id)
      .build()
      .toUri();
    var request = RequestEntity.get(uri)
      .header("X-Cassandra-Token", token)
      .build();

    var response = restTemplate.exchange(request, cls);

    return response.getBody();
  }

  public void patchSubDocument(String collection, String id, String key, Map<String, Object> updates) {
    var updateUri = UriComponentsBuilder.fromHttpUrl(baseUrl)
      .pathSegment("collections", collection, id, key)
      .build()
      .toUri();
    var updateRequest = RequestEntity.patch(updateUri)
      .header("X-Cassandra-Token", token)
      .body(updates);
    restTemplate.exchange(updateRequest, Map.class);
  } 
}
