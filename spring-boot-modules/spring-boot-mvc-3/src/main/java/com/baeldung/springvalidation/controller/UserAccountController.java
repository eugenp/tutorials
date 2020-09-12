package com.baeldung.springvalidation.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baeldung.springvalidation.domain.UserAccount;
import com.baeldung.springvalidation.interfaces.BasicInfo;

@Controller
public class UserAccountController {

    @GetMapping("/getUserForm")
    public String showUserForm(Model theModel) {
        UserAccount theUser = new UserAccount();
        theModel.addAttribute("useraccount", theUser);
        return "userHome";
    }

    @RequestMapping(value = "/saveBasicInfo", method = RequestMethod.POST)
    public String saveBasicInfo(@Valid @ModelAttribute("useraccount") UserAccount useraccount, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "error";
        }
        return "success";
    }

    @RequestMapping(value = "/saveBasicInfoStep1", method = RequestMethod.POST)
    public String saveBasicInfoStep1(@Validated(BasicInfo.class) @ModelAttribute("useraccount") UserAccount useraccount, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "error";
        }
        return "success";
    }

}
