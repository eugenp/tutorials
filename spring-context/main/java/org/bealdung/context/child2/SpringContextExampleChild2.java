package org.bealdung.context.child2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/child2controller")
public class SpringContextExampleChild2 {

	public SpringContextExampleChild2(){
        System.out.println("Child2 Controller");
    }
     
    @Autowired
    ApplicationContext context;
     
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public @ResponseBody String get() {              
    	System.out.println("Spring Root Context Example - Child2");
    	return "Spring Root Context Example Child2";
    }
}
