package com.baeldung.thymeleaf.booleanexpressions;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller to test boolean expressions
 * 
 */
@Controller
public class BooleanExpressionsController {

    @RequestMapping(value = "/booleans", method = RequestMethod.GET)
    public String getDates(Model model) {
        // "truthy" values
        model.addAttribute("trueValue", true);
        model.addAttribute("one", 1);
        model.addAttribute("nonZeroCharacter", 'a');
        model.addAttribute("emptyString", "");
        model.addAttribute("foo", "foo");
        model.addAttribute("object", new Object());
        model.addAttribute("arrayOfZeros", new Integer[] { 0, 0 });
        model.addAttribute("arrayOfZeroAndOne", new Integer[] { 0, 1 });
        model.addAttribute("arrayOfOnes", new Integer[] { 1, 1 });

        // "falsy" values
        model.addAttribute("nullValue", null);
        model.addAttribute("falseValue", false);
        model.addAttribute("zero", 0);
        model.addAttribute("zeroCharacter", '\0');
        model.addAttribute("falseString", "false");
        model.addAttribute("no", "no");
        model.addAttribute("off", "off");
        
        model.addAttribute("isRaining", true);
        model.addAttribute("isSunny", true);
        model.addAttribute("isCold", false);
        model.addAttribute("isWarm", true);

        return "booleans.html";
    }

}
