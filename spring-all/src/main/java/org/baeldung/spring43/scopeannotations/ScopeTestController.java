package org.baeldung.spring43.scopeannotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
public class ScopeTestController {

    @Autowired
    private LoginAction loginAction;

    @Autowired
    private UserPreferences userPreferences;

    @Autowired
    private AppPreferences appPreferences;

    @GetMapping("/request")
    public String getRequestNumber() {
        return Integer.toString(loginAction.getInstanceNumber());
    }

    @GetMapping("/session")
    public String getSessionNumber() {
        return Integer.toString(userPreferences.getInstanceNumber());
    }

    @GetMapping("/application")
    public String getApplicationNumber() {
        return Integer.toString(appPreferences.getInstanceNumber());
    }

}
