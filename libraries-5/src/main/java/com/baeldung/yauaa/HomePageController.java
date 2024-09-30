package com.baeldung.yauaa;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.ModelAndView;

import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;

@Controller
public class HomePageController {

    private final UserAgentAnalyzer userAgentAnalyzer;

    public HomePageController(UserAgentAnalyzer userAgentAnalyzer) {
        this.userAgentAnalyzer = userAgentAnalyzer;
    }

    @GetMapping(value = "/home")
    public ModelAndView homePage(@RequestHeader(value = HttpHeaders.USER_AGENT) String userAgentString) {
        UserAgent userAgent = userAgentAnalyzer.parse(userAgentString);
        String osSystemClass = userAgent.getValue(UserAgent.OPERATING_SYSTEM_CLASS);
        boolean isMobileDevice = osSystemClass.equals("Mobile");

        if (isMobileDevice) {
            return new ModelAndView("/mobile-home");
        }
        return new ModelAndView("error/open-in-mobile", HttpStatus.FORBIDDEN);
    }

}