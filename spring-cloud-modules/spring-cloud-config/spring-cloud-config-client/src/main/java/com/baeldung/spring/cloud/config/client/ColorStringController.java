package com.baeldung.spring.cloud.config.client;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/String")
public class ColorStringController {

    @Value("${user.role:temp}")
    private String role;

    @Value("${user.password:temp}")
    private String password;

    @Value("${color:blue}")
    private String color;

    @GetMapping("/colors")
    public ModelAndView getColor(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("color");
        modelAndView.addObject("color", color);

        return modelAndView;
    }


    @GetMapping("/colorString")
    public String getColorString()
    {
        return color;
    }

    @GetMapping(value = "/whoami/{username}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String whoami(@PathVariable("username") String username) {
        return String.format("Hello %s! You are a(n) %s and your password is '%s'.\n", username, role, password);
    }
}
