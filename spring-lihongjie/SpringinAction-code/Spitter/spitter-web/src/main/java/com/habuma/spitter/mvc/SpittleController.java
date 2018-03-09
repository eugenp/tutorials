package com.habuma.spitter.mvc;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.habuma.spitter.domain.Spittle;
import com.habuma.spitter.service.SpitterService;

@Controller
@RequestMapping("/spittles")
public class SpittleController {
  private SpitterService spitterService;
  
  @Inject
  public SpittleController(SpitterService spitterService) {
    this.spitterService = spitterService;
  }
  
  @RequestMapping(method=RequestMethod.GET)
  public String getRecentSpittles(Model model) {
    model.addAttribute(spitterService.getRecentSpittles(20));
    return "spittles/list";
  }
  
  @RequestMapping(method=RequestMethod.POST, headers="Accept=text/html")
  public String createSpittleFromForm(@Valid Spittle spittle, 
                     BindingResult result, HttpServletResponse response) 
                     throws BindException {
    if(result.hasErrors()) {
      throw new BindException(result);
    }
    
    spitterService.saveSpittle(spittle);
    
    return "redirect:/";
  }
  
  
  //<start id="method_addNewSpittle"/> 
  @RequestMapping(method=RequestMethod.POST) //<co id="co_handlePOST"/> 
  @ResponseStatus(HttpStatus.CREATED) // <co id="co_createdResponse" />
  public @ResponseBody Spittle createSpittle(@Valid Spittle spittle, 
                     BindingResult result, HttpServletResponse response) 
                     throws BindException {
    if(result.hasErrors()) {
      throw new BindException(result);
    }
    
    spitterService.saveSpittle(spittle);
    
    response.setHeader("Location", "/spittles/" + spittle.getId()); //<co id="co_setLocationHeader"/>
    return spittle; //<co id="co_returnSpittle"/>
  }
  //<end id="method_addNewSpittle"/> 

  //<start id="method_updateSpittle"/> 
  @RequestMapping(value="/{id}", method=RequestMethod.PUT)
  public String updateSpittle(@PathVariable("id") long id,
          @Valid Spittle spittle) {
    spitterService.saveSpittle(spittle);
    return "spittles/view";
  }
  //<end id="method_updateSpittle"/> 
  
  //<start id="method_getSpittle"/> 
  @RequestMapping(value="/{id}", method=RequestMethod.GET)
  public String getSpittle(@PathVariable("id") long id,
          Model model) {
    model.addAttribute(spitterService.getSpittleById(id));
    return "spittles/view";
  }
  //<end id="method_getSpittle"/> 

  //<start id="method_deleteSpittle"/> 
  @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteSpittle(@PathVariable("id") long id) {
    spitterService.deleteSpittle(id);
  }
  //<end id="method_deleteSpittle"/> 

  // Machine-friendly RESTful methods follow
  @RequestMapping(method=RequestMethod.GET, 
                  headers="Accept=application/json")
  public @ResponseBody List<Spittle> getRecentSpittles() {
    return spitterService.getRecentSpittles(20);
  }

  @RequestMapping(value="/{id}", method=RequestMethod.GET,
                  headers="Accept=application/json")
  public @ResponseBody 
  Spittle getSpittle(@PathVariable("id") long id) {
    return spitterService.getSpittleById(id);
  }  
  
  //<start id="method_putSpittle"/> 
  @RequestMapping(value="/{id}", method=RequestMethod.PUT)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void putSpittle(@PathVariable("id") long id,
          @Valid Spittle spittle) {
    spitterService.saveSpittle(spittle);
  }
  //<end id="method_putSpittle"/>
  
  
  @ExceptionHandler(BindException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public void handleBindException() {}
}
