package testjUnit;

import org.apache.log4j.Logger;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebServicesController {

    public static final Logger logger = Logger.getLogger(WebServicesController.class);

    @Resource(name="requestService")
    WebServices requestService;
    
    @Resource(name="sessionService")
    WebServices sessionService;
    
    @RequestMapping("/testjUnit")
    public String getScopes(final Model model){
        logger.info("Request Service: " + requestService.getService());
        logger.info("Session service: " + sessionService.getService());
        requestService.setService("You have Google fiber for your internet provider");
        sessionService.setService("You have AT&T for your internet provider");
        model.addAttribute("requestMessage", requestService.getService());
        model.addAttribute("sessionMessage", sessionService.getService());
        return "scopesWebServices";
    }
}
