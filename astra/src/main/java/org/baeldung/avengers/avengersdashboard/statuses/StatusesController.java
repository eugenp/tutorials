package org.baeldung.avengers.avengersdashboard.statuses;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StatusesController {
  @Autowired
  private StatusesService statusesService;

  @GetMapping("/")
  public ModelAndView getStatuses() {
    var result = new ModelAndView("dashboard");
    result.addObject("statuses", statusesService.getStatuses());

    return result;
  }
}
