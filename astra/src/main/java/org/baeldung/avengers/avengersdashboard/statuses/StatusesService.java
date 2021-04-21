package org.baeldung.avengers.avengersdashboard.statuses;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.baeldung.avengers.avengersdashboard.astra.DocumentClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusesService {
  @Autowired
  private DocumentClient client;

  public List<Status> getStatuses() {
    var collection = client.getCollection("statuses", Statuses.class);
    return new ArrayList<>(collection.data().values());
  }

  public void updateStatus(String avenger, String location, String status) throws Exception {
    var collection = client.getCollection("statuses", Statuses.class);
    collection.data().entrySet().stream()
      .filter(entry -> entry.getValue().avenger().equals(avenger))
      .map(entry -> entry.getKey())
      .findFirst()
      .ifPresent(key -> {
        client.patchDocument("statuses", key, Map.of("location", location, "status", status));
      });
  }
}
