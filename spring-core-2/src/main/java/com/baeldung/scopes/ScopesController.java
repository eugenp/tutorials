package com.baeldung.scopes;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ScopesController {
    public static final Logger LOG = LoggerFactory.getLogger(ScopesController.class);

    private static final String NAME_P_STRING = "previousMessage";
    private static final String NAME_C_STRING = "currentMessage";
    private static final String RETURN_STRING = "currentMessage";

    @Resource(name = "requestScopedBean")
    HelloMessageGenerator requestScopedBean;

    @Resource(name = "sessionScopedBean")
    HelloMessageGenerator sessionScopedBean;

    @Resource(name = "applicationScopedBean")
    HelloMessageGenerator applicationScopedBean;

    @RequestMapping("/scopes/request")
    public String getRequestScopeMessage(final Model model) {
        model.addAttribute(NAME_P_STRING, requestScopedBean.getMessage());
        requestScopedBean.setMessage("Request Scope Message!");
        model.addAttribute(NAME_C_STRING, requestScopedBean.getMessage());
        return RETURN_STRING;
    }

    @RequestMapping("/scopes/session")
    public String getSessionScopeMessage(final Model model) {
        model.addAttribute(NAME_P_STRING, sessionScopedBean.getMessage());
        sessionScopedBean.setMessage("Session Scope Message!");
        model.addAttribute(NAME_C_STRING, sessionScopedBean.getMessage());
        return RETURN_STRING;
    }

    @RequestMapping("/scopes/application")
    public String getApplicationScopeMessage(final Model model) {
        model.addAttribute(NAME_P_STRING, applicationScopedBean.getMessage());
        applicationScopedBean.setMessage("Application Scope Message!");
        model.addAttribute(NAME_C_STRING, applicationScopedBean.getMessage());
        return RETURN_STRING;
    }
}
