package com.habuma.spitter.mvc.restless;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.habuma.spitter.service.SpitterService;

@Controller
public class ShowSpitterController {
  @RequestMapping(value="/showSpitter",        //<co id="co_verbyUrl"/>  
                  method=RequestMethod.GET)
  public String showSpitter(@RequestParam String username, //<co id="co_usernameParam"/> 
                            Map<String, Object> model) {
    model.put("spitter", spitterService.getSpitter(username));
    return "spitter";
  }
  
  @Autowired
  SpitterService spitterService;
}
