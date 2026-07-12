package com.baeldung.auth.server.dynamicscopes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Set;

@Controller
public class ConsentController {
    private static final Logger log = LoggerFactory.getLogger(ConsentController.class);

    private final RegisteredClientRepository registeredClientRepository;
    private final OAuth2AuthorizationConsentService authorizationConsentService;

    public ConsentController(RegisteredClientRepository registeredClientRepository, OAuth2AuthorizationConsentService authorizationConsentService) {
        this.registeredClientRepository = registeredClientRepository;
        this.authorizationConsentService = authorizationConsentService;
    }

    @GetMapping("/consent")
    public String consent(Principal principal, Model model,
      @RequestParam(name = OAuth2ParameterNames.CLIENT_ID) String clientId,
      @RequestParam(name = OAuth2ParameterNames.SCOPE) String scope,
      @RequestParam(name = OAuth2ParameterNames.STATE) String state) {

        log.info("Principal: {}", principal);

        var client = registeredClientRepository.findByClientId(clientId);
        assert client != null;
        var currentConsent =  authorizationConsentService.findById(client.getId(), principal.getName());
        Set<String> authorizedScopes = currentConsent != null ? currentConsent.getScopes() : Set.of();

        // Remove already authorized scopes from the requested scopes and the special 'openid' scope.
        var neededScopes = Set.of(scope.split(" ")).stream()
          .filter(s -> !authorizedScopes.contains(s) && !OidcScopes.OPENID.equals(s))
          .toList();


        model.addAttribute("clientId", clientId);
        model.addAttribute("clientName", client.getClientName() != null ? client.getClientName() : client.getClientId());
        model.addAttribute("scopes", neededScopes);
        model.addAttribute("state", state);
        model.addAttribute("authorizedScopes", authorizedScopes);

        return "consent";

   }
}
