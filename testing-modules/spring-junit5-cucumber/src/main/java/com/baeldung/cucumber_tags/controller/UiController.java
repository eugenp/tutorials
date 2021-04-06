package com.baeldung.cucumber_tags.controller;

import com.baeldung.cucumber_tags.service.RandomNumberGeneratorService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@AllArgsConstructor
public class UiController {

    @GetMapping("/random-number-generator")
    public String showForm(Model model) {
        RandomNumberQuery randomNumberQuery = new RandomNumberQuery();
        model.addAttribute("randomNumberQuery", randomNumberQuery);

        return "random-number-generator";
    }

    @PostMapping(value = "/random-number-generator")
    public String generateRandomNumber(@ModelAttribute("randomNumberQuery") final RandomNumberQuery randomNumberQuery) {
        RandomNumberGeneratorService service = new RandomNumberGeneratorService();
        randomNumberQuery.randomNumber = service.generateRandomNumber(randomNumberQuery.min, randomNumberQuery.max);
        return "random-number-generator";
}

    @Data
    private static class RandomNumberQuery {
        Integer min = null;
        Integer max = null;
        Integer randomNumber = null;
    }
}
