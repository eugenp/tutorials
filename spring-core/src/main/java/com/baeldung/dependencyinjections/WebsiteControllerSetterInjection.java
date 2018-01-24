package com.baeldung.dependencyinjections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebsiteControllerSetterInjection {

    private HomepageService homeService;

    private AboutuspageService aboutusService;

    @Autowired
    public void setHomepageService(HomepageService homeService) {
        this.homeService = homeService;
    }

    @Autowired
    public void setAboutuspageService(AboutuspageService aboutusService) {
        this.aboutusService = aboutusService;
    }

    public String getContentsViaSetterInjection() {
        StringBuffer websiteContents = new StringBuffer();
        websiteContents.append(homeService.getContents());
        websiteContents.append(" and ");
        websiteContents.append(aboutusService.getContents());
        return websiteContents.toString();
    }
}
