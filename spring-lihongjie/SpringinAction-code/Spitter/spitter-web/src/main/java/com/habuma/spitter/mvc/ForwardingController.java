package com.habuma.spitter.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wallsc
 */
@Controller
public class ForwardingController {
  
  @RequestMapping("/{spitterId}")
  public String showSpittlesForSpitter(@PathVariable String spitterId) {
    return "forward:/app/spitters/"+spitterId;
  }
  
  @RequestMapping("/signup")
  public String signup() {
    return "forward:/app/spitters/form";
  }
}
