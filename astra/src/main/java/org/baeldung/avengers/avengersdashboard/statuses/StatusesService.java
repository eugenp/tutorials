package org.baeldung.avengers.avengersdashboard.statuses;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

@Service
public class StatusesService {

  @Value("https://${ASTRA_DB_ID}-${ASTRA_DB_REGION}.apps.astra.datastax.com/api/rest/v2/namespaces/${ASTRA_DB_KEYSPACE}")
  private String baseUrl;

  @Value("${ASTRA_DB_APPLICATION_TOKEN}")
  private String token;

  @Autowired
  private ObjectMapper objectMapper;

  private RestTemplate restTemplate;

  public StatusesService() {
    this.restTemplate = new RestTemplate();
    this.restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
  }

  public List<Status> getStatuses() {
    var uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
      .pathSegment("collections", "statuses")
      .queryParam("page-size", "20")
      .build()
      .toUri();
    var request = RequestEntity.get(uri)
      .header("X-Cassandra-Token", token)
      .build();

    var response = restTemplate.exchange(request, Statuses.class);
    var statuses = response.getBody().data();
    return new ArrayList<>(statuses.values());
  }

  public void updateStatus(String avenger, String location, String status) throws Exception {
    // Firstly, find the status to be updated.
    var query = Map.of("avenger", Map.of("$eq", avenger));
    var queryUri = UriComponentsBuilder.fromHttpUrl(baseUrl)
      .pathSegment("collections", "statuses")
      .queryParam("page-size", "1")
      .queryParam("where", objectMapper.writeValueAsString(query))
      .build()
      .toUri();

    var queryRequest = RequestEntity.get(queryUri)
      .header("X-Cassandra-Token", token)
      .build();
    var queryResponse = restTemplate.exchange(queryRequest, Statuses.class);
    var statuses = queryResponse.getBody().data();

    // If we found one then update it with the new details.
    if (statuses.keySet().size() == 1) {
      var key = statuses.keySet().iterator().next();
      var updateUri = UriComponentsBuilder.fromHttpUrl(baseUrl)
        .pathSegment("collections", "statuses", key)
        .build()
        .toUri();
      var updateBody = Map.of("location", location, "status", status);
      var updateRequest = RequestEntity.patch(updateUri)
        .header("X-Cassandra-Token", token)
        .body(updateBody);
      var updateResponse = restTemplate.exchange(updateRequest, Map.class);
    }
  }
}
