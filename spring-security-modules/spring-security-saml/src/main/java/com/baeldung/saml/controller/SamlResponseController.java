package com.baeldung.saml.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The SAML auth workflow is initiated by redirecting the request to the /doSaml
 * endpoint. When the SAML flow is complete, the browser will be directed back to /doSaml.
 *
 * This class handles the result of that flow. If the user is successfully authenticated,
 * they should be directed to the guarded landing page.
 *
 * @see IndexController
 * @see com.baeldung.saml.config.WebSecurityConfig
 *
 * @author jcavazos
 */
@Controller
public class SamlResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SamlResponseController.class);

    @GetMapping(value = "/doSaml")
    public String handleSamlAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("doSaml auth result: {}", auth);
        if (auth != null) {
            return "redirect:/landing";
        } else {
            return "/";
        }
    }
}
