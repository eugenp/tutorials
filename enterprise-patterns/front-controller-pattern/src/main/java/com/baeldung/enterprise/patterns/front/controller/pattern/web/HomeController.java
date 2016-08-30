package com.baeldung.enterprise.patterns.front.controller.pattern.web;

import com.baeldung.enterprise.patterns.front.controller.pattern.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @Autowired
    private Map<String, List<Task>> taskMap;

    @GetMapping("/*")
    public String home(Model model) {
        List<String> users = taskMap.keySet().stream()
          .sorted()
          .collect(Collectors.toList());
        model.addAttribute("users", users);
        return "home";
    }
}
