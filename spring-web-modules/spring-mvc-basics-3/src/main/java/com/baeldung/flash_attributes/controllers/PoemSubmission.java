package com.baeldung.flash_attributes.controllers;

import com.baeldung.flash_attributes.model.Poem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PoemSubmission {

    @GetMapping("/poem/success")
    public String getSuccess(HttpServletRequest request) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            Poem poem = (Poem) inputFlashMap.get("poem");
            return "success";
        } else {
            return "redirect:/poem/submit";
        }
    }

    @PostMapping("/poem/submit")
    public RedirectView submitPost(
        HttpServletRequest request, 
        @ModelAttribute Poem poem, 
        RedirectAttributes redirectAttributes) {
        if (Poem.isValidPoem(poem)) {
            redirectAttributes.addFlashAttribute("poem", poem);
            return new RedirectView("/poem/success", true);
        } else {
            return new RedirectView("/poem/submit", true);
        }
    }

    @GetMapping("/poem/submit")
    public String submitGet(Model model) {
        model.addAttribute("poem", new Poem());
        return "submit";
    }

}