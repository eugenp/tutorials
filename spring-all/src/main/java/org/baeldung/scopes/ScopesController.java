package org.baeldung.scopes;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ScopesController {
    public static final Logger LOG = LoggerFactory.getLogger(ScopesController.class);

    @Resource(name = "requestMessage")
    HelloMessageGenerator requestMessage;

    @Resource(name = "sessionMessage")
    HelloMessageGenerator sessionMessage;

    @RequestMapping("/scopes")
    public String getScopes(final Model model) {
        LOG.info("Request Message:" + requestMessage.getMessage());
        LOG.info("Session Message" + sessionMessage.getMessage());
        requestMessage.setMessage("Good morning!");
        sessionMessage.setMessage("Good afternoon!");
        model.addAttribute("requestMessage", requestMessage.getMessage());
        model.addAttribute("sessionMessage", sessionMessage.getMessage());
        return "scopesExample";
    }

}
