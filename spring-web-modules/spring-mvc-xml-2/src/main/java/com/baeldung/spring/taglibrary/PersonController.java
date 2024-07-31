package com.baeldung.spring.taglibrary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PersonController {

    @Autowired
    PersonValidator validator;

    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public ModelAndView showForm(final Model model) {

        initData(model);
        return new ModelAndView("personForm", "person", new Person());
    }

    @RequestMapping(value = "/addPerson", method = RequestMethod.POST)
    public String submit(@Valid @ModelAttribute("person") final Person person, final BindingResult result, final ModelMap modelMap, final Model model) {

        validator.validate(person, result);

        if (result.hasErrors()) {

            initData(model);
            return "personForm";
        }

        modelMap.addAttribute("person", person);

        return "personView";
    }

    private void initData(final Model model) {

        final List<String> favouriteLanguageItem = new ArrayList<String>();
        favouriteLanguageItem.add("Java");
        favouriteLanguageItem.add("C++");
        favouriteLanguageItem.add("Perl");
        model.addAttribute("favouriteLanguageItem", favouriteLanguageItem);

        final List<String> jobItem = new ArrayList<String>();
        jobItem.add("Full time");
        jobItem.add("Part time");
        model.addAttribute("jobItem", jobItem);

        final Map<String, String> countryItems = new LinkedHashMap<String, String>();
        countryItems.put("US", "United Stated");
        countryItems.put("IT", "Italy");
        countryItems.put("UK", "United Kingdom");
        countryItems.put("FR", "Grance");
        model.addAttribute("countryItems", countryItems);

        final List<String> fruit = new ArrayList<String>();
        fruit.add("Banana");
        fruit.add("Mango");
        fruit.add("Apple");
        model.addAttribute("fruit", fruit);

        final List<String> books = new ArrayList<String>();
        books.add("The Great Gatsby");
        books.add("Nineteen Eighty-Four");
        books.add("The Lord of the Rings");
        model.addAttribute("books", books);
    }
}
