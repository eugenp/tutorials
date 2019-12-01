package controllers;

import ninja.Result;
import ninja.Results;
import services.UserService;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Singleton;

@Singleton
public class ApplicationController {
    
    private static Log logger = LogFactory.getLog(ApplicationController.class);
    
    @Inject
    UserService userService;

    public Result index() {
        return Results.html();
    }
    
    public Result helloWorldJson() {
        SimplePojo simplePojo = new SimplePojo();
        simplePojo.content = "Hello World! Hello Json!";
        logger.info(userService.getUserName());
        return Results.json().render(simplePojo);
    }
    
    public static class SimplePojo {
        public String content;
    }
    
}
