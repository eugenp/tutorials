package org.baeldung.avengers.avengersdashboard.statuses;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UpdateController {
  @Autowired
  private StatusesService statusesService;

  @PostMapping("/update/{avenger}")
  public void getStatuses(@PathVariable String avenger, @RequestBody UpdateBody body) throws Exception {
    statusesService.updateStatus(avenger, body.location(), body.status());
  }

  private static record UpdateBody(String location, String status) {}
}
