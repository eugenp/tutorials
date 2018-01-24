package com.baeldung.dependencyinjections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebsiteControllerConstructorInjection {

    private HomepageService homeService;

    private AboutuspageService aboutusService;

    @Autowired
    public WebsiteControllerConstructorInjection(HomepageService homeService, AboutuspageService aboutusService) {
        this.homeService = homeService;
        this.aboutusService = aboutusService;
    }

    public String getContentsViaConstructorInjection() {
        StringBuffer websiteContents = new StringBuffer();
        websiteContents.append(homeService.getContents());
        websiteContents.append(" and ");
        websiteContents.append(aboutusService.getContents());
        return websiteContents.toString();
    }

}
