package org.bealdung.context.child1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/child1controller")
public class SpringContextExampleChild1 {

	public SpringContextExampleChild1(){
        System.out.println("Child1 Controller");
    }
     
    @Autowired
    ApplicationContext context;
     
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public @ResponseBody String get() {              
    	System.out.println("Spring Root Context Example - Child1");
    	return "Spring Root Context Example Child1";
    }
}
