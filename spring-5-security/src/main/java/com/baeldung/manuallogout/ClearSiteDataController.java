package com.baeldung.manuallogout;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ClearSiteDataController {

    Directive[] SOURCE = {Directive.COOKIES, Directive.STORAGE, Directive.EXECUTION_CONTEXTS, Directive.CACHE};

    @RequestMapping(value = {"/csdlogout"}, method = RequestMethod.POST)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            ClearSiteDataHeaderWriter csdHeaderWriter = new ClearSiteDataHeaderWriter(SOURCE);
            new HeaderWriterLogoutHandler(csdHeaderWriter).logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
}
