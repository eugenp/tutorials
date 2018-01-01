package org.baeldung.persistence.server;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.katharsis.resource.registry.RegistryEntry;
import io.katharsis.resource.registry.ResourceRegistry;
import io.katharsis.spring.boot.v3.KatharsisConfigV3;


@RestController
@Import({KatharsisConfigV3.class})
public class KatharsisController {

  @Autowired
  private ResourceRegistry resourceRegistry;

  @RequestMapping("/resources-info")
  public Map<String, String> getResources() {
    Map<String, String> result = new HashMap<>();
    // Add all resources
    for (RegistryEntry entry : resourceRegistry.getResources()) {
      result.put(entry.getResourceInformation().getResourceType(),
          resourceRegistry.getResourceUrl(entry.getResourceInformation()));
    }
    return result;
  }
}
