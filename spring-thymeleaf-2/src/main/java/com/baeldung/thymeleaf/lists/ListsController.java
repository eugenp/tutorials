package com.baeldung.thymeleaf.lists;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lists")
public class ListsController {

    @GetMapping("/toList")
    public String usingToList(Model model) {
        List<String> colors = getColors();
        String[] colorsArray = colors.toArray(new String[0]);
        model.addAttribute("myArray", colorsArray);
        return "lists/toList";
    }

    @GetMapping("/contains")
    public String usingContains(Model model) {
        model.addAttribute("myList", getColors());
        model.addAttribute("others", getOtherColors());
        return "lists/contains";
    }

    @GetMapping("/size")
    public String usingSize(Model model) {
        model.addAttribute("myList", getColors());
        return "lists/size";
    }

    @GetMapping("/isEmpty")
    public String usingIsEmpty(Model model) {
        model.addAttribute("myList", getColors());
        return "lists/isEmpty";
    }

    @GetMapping("/sort")
    public String usingSort(Model model) {
        model.addAttribute("myList", getColors());
        model.addAttribute("reverse", Comparator.reverseOrder());
        return "lists/sort";
    }

    private List<String> getColors() {
        List<String> colors = new ArrayList<>();
        colors.add("green");
        colors.add("yellow");
        colors.add("red");
        colors.add("blue");
        return colors;
    }

    private List<String> getOtherColors() {
        List<String> colors = new ArrayList<>();
        colors.add("green");
        colors.add("blue");
        return colors;
    }
}
