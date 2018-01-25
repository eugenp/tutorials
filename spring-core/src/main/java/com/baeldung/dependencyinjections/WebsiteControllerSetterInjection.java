package com.baeldung.dependencyinjections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebsiteControllerSetterInjection {

    private HomepageService homeService;

    @Autowired
    public void setHomepageService(HomepageService homeService) {
        this.homeService = homeService;
    }

    public String getContentsViaSetterInjection() {
        return homeService.getContents();
    }
}
