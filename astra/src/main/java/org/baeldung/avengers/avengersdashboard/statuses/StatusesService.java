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
    var collection = client.getDocument("statuses", "latest", Statuses.class);
    return new ArrayList<>(collection.data().values());
  }

  public void updateStatus(String avenger, String location, String status) throws Exception {
    client.patchSubDocument("statuses", "latest", avenger, Map.of("location", location, "status", status));
  }
}
