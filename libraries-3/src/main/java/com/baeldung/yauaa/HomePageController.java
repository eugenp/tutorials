package com.baeldung.yauaa;

import java.util.List;

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

    private static final List<String> SUPPORTED_DEVICES = List.of("Mobile", "Tablet", "Phone");
    private final UserAgentAnalyzer userAgentAnalyzer;

    public HomePageController(UserAgentAnalyzer userAgentAnalyzer) {
        this.userAgentAnalyzer = userAgentAnalyzer;
    }

    @GetMapping("/mobile/home")
    public ModelAndView homePage(@RequestHeader(HttpHeaders.USER_AGENT) String userAgentString) {
        UserAgent userAgent = userAgentAnalyzer.parse(userAgentString);
        String deviceClass = userAgent.getValue(UserAgent.DEVICE_CLASS);
        boolean isMobileDevice = SUPPORTED_DEVICES.contains(deviceClass);

        if (isMobileDevice) {
            return new ModelAndView("/mobile-home");
        }
        return new ModelAndView("error/open-in-mobile", HttpStatus.FORBIDDEN);
    }

}