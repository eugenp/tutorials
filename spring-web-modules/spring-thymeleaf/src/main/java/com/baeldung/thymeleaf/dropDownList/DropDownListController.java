package com.baeldung.thymeleaf.dropDownList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DropDownListController {

    @RequestMapping(value = "/populateDropDownList", method = RequestMethod.GET) public String populateList(Model model) {
        List<String> options = new ArrayList<String>();
        options.add("option 1");
        options.add("option 2");
        options.add("option 3");
        options.add("option 4");
        model.addAttribute("options", options);
        return "dropDownList/dropDownList.html";
    }

}
