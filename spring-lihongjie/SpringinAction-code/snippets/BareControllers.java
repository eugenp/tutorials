// <start id="java_SpitterController" /> 
package com.habuma.spitter.mvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import com.habuma.spitter.domain.Spitter;
import com.habuma.spitter.service.SpitterService;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping("/spitter")   //<co id="co_class_request_mapping"/>
public class SpitterController {

  private final SpitterService spitterService;

  @Inject
  public SpitterController(SpitterService spitterService) {
    this.spitterService = spitterService;
  }
    
  @RequestMapping(value="/spittles", method=GET)  //<co id="co_method_request_mapping"/>
  public String listSpittlesForSpitter(
        @RequestParam("spitter") String username, Model model) {
    Spitter spitter = spitterService.getSpitter(username);
    model.addAttribute(spitter); //<co id="co_fill_model_2"/>
    model.addAttribute(spitterService.getSpittlesForSpitter(username));
    return "spittles/list";
  }
}
//<end id="java_SpitterController" />





//<start id="java_SpittleController" />
package com.habuma.spitter.mvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.habuma.spitter.service.SpitterService;

@Controller
@RequestMapping("/spitter")
public class SpitterController {

  // ...
      
  @Autowired
  SpitterService spitterService;
}
//<end id="java_SpitterController" />


//<start id="method_spittlesForSpitter_nonREST" />
@RequestMapping(value="/spittles", method=GET)
public String listSpittlesForSpitter(
        @RequestParam("spitter") String username, 
        Map<String, Object> model) {
    Spitter spitter = spitterService.getSpitter(username);
    model.put("spitter", spitter);
    model.put("spittles", 
            spitterService.getSpittlesForSpitter(spitter));
    return "spittles";
}
//<end id="method_spittlesForSpitter_nonREST" />


