package com.baeldung.thymeleaf.pathvariables;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PathVariablesController {
    private List<Item> items = new ArrayList<Item>();
    
    public PathVariablesController() {
        Item item1 = new Item(1, "First Item");
        List<Detail> item1Details = new ArrayList<>();
        item1Details.add(new Detail(1, "Green"));
        item1Details.add(new Detail(2, "Large"));
        item1.setDetails(item1Details);
        items.add(item1);
        
        Item item2 = new Item(2, "Second Item");
        List<Detail> item2Details = new ArrayList<>();
        item2Details.add(new Detail(1, "Red"));
        item2Details.add(new Detail(2, "Medium"));
        item2.setDetails(item2Details);
        items.add(item2);
    }

    @GetMapping("/pathvars")
    public String start(Model model) {        
        model.addAttribute("items", items);
        return "pathvariables/index";
    }
    
    @GetMapping("/pathvars/single/{id}")
    public String singlePathVariable(@PathVariable("id") int id, Model model) {
        if (id == 1) {
            model.addAttribute("item", new Item(1, "First Item"));
        } else {
            model.addAttribute("item", new Item(2, "Second Item"));
        }
        
        return "pathvariables/view";
    }
    
    @GetMapping("/pathvars/item/{itemId}/detail/{detailId}")
    public String multiplePathVariable(@PathVariable("itemId") int itemId, @PathVariable("detailId") int detailId, Model model) {
        for (Item item : items) {
            if (item.getId() == itemId) {
                model.addAttribute("item", item);
                for (Detail detail : item.getDetails()) {
                    if (detail.getId() == detailId) {
                        model.addAttribute("detail", detail);
                    }
                }
            }
        }
        return "pathvariables/view";
    }
}
