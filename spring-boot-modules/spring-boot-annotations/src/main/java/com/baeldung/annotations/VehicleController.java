package com.baeldung.annotations;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/vehicles", method = RequestMethod.GET)
public class VehicleController {

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @PostMapping("/save")
    public void saveVehicle(@RequestBody Vehicle vehicle) {
    }

    @RequestMapping("/{id}")
    public Vehicle getVehicle(@PathVariable("id") long id) {
        return null;
    }

    @RequestMapping
    public Vehicle getVehicleByParam(@RequestParam("id") long id) {
        return null;
    }

    @RequestMapping("/buy")
    public Car buyCar(@RequestParam(defaultValue = "5") int seatCount) {
        return null;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void onIllegalArgumentException(IllegalArgumentException exception) {
    }

    @PostMapping("/assemble")
    public void assembleVehicle(@ModelAttribute("vehicle") Vehicle vehicle) {
    }

    @ModelAttribute("vehicle")
    public Vehicle getVehicle() {
        return null;
    }

}
