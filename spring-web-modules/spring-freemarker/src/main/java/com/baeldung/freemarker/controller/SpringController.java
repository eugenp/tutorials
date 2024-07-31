package com.baeldung.freemarker.controller;

import java.util.*;

import com.baeldung.freemarker.method.LastCharMethod;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.Version;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baeldung.freemarker.model.Car;

@Controller
public class SpringController {

    private static List<Car> carList = new ArrayList<Car>();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        return "redirect:/cars";
    }

    static {
        carList.add(new Car("Honda", "Civic"));
        carList.add(new Car("Toyota", "Camry"));
        carList.add(new Car("Nissan", "Altima"));
    }

    @RequestMapping(value = "/cars", method = RequestMethod.GET)
    public String init(@ModelAttribute("model") ModelMap model) {
        model.addAttribute("carList", carList);
        return "index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addCar(@ModelAttribute("car") Car car) {
        if (null != car && null != car.getMake() && null != car.getModel() && !car.getMake().isEmpty() && !car.getModel().isEmpty()) {
            carList.add(car);
        }
        return "redirect:/cars";
    }

    @RequestMapping(value = "/commons", method = RequestMethod.GET)
    public String showCommonsPage(Model model) {
        model.addAttribute("statuses", Arrays.asList("200 OK", "404 Not Found", "500 Internal Server Error"));
        model.addAttribute("lastChar", new LastCharMethod());
        model.addAttribute("random", new Random());
        model.addAttribute("statics", new DefaultObjectWrapperBuilder(new Version("2.3.28")).build().getStaticModels());
        return "commons";
    }
}