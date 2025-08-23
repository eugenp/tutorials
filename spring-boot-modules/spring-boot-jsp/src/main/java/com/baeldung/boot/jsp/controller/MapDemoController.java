package com.baeldung.boot.jsp.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/map-demo")
public class MapDemoController {

    private final static Map<String, String> movies = Map.of(
    // @formatter:off
      "M-01", "No Country for Old Men",
      "M-02", "The Silence of the Lambs",
      "M-03", "Back to the Future",
      "M-04", "Gone with the Wind",
      "M-05", "The Girl with the Dragon Tattoo"
    // @formatter:on
    );

    @GetMapping("/using-scriptlets")
    public String usingScriplets(Model model) {
        model.addAttribute("movieMap", movies);
        return "map-demo/using-scriptlets";
    }

    @GetMapping("/using-jstl")
    public String usingJstl(Model model) {
        model.addAttribute("movieMap", movies);
        return "map-demo/using-jstl";

    }
}