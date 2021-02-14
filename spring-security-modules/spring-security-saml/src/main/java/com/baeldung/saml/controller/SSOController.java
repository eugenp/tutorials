package com.baeldung.saml.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml.metadata.MetadataManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Set;

/**
 * Page to select which Identity Provider (IDP) to authenticate against when beginning SAML auth flow.
 *
 */
@Controller
@RequestMapping("/saml")
public class SSOController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SSOController.class);

    private final MetadataManager metadata;

    @Autowired
    public SSOController(MetadataManager metadata) {
        this.metadata = metadata;
    }

    @RequestMapping(value = "/discovery", method = RequestMethod.GET)
    public String idpSelection(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("Current auth: {}", auth==null?"NULL":auth.getPrincipal());
        if (auth == null || (auth instanceof AnonymousAuthenticationToken)) {
            Set<String> idps = metadata.getIDPEntityNames();
            idps.forEach(idp -> LOGGER.info("Configured Identity Provider for SSO: {}", idp));
            model.addAttribute("idps", idps);
            return "discovery";
        } else {
            LOGGER.warn("The current user is already logged in: {}", auth);
            return "redirect:/landing";
        }
    }
}
