package com.habuma.spitter.mvc.restless;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.habuma.spitter.domain.Spitter;
import com.habuma.spitter.domain.Spittle;
import com.habuma.spitter.service.SpitterService;

@Controller
@RequestMapping("/displaySpittles.htm")
public class DisplaySpittlesForSpitterController {
  
  @RequestMapping(method=RequestMethod.GET)
  public String showSpittlesForSpitter(
                          @RequestParam("spitterId") long spitterId, 
                          Map<String, Object> model) {
    
    Spitter spitter = spitterService.getSpitter(spitterId);
    List<Spittle> spittles = 
                      spitterService.getSpittlesForSpitter(spitter);
    model.put("spittles", spittles);
    
    return "spittles/list";
  }
  
  @Autowired
  SpitterService spitterService;
}
