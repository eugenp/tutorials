package com.baeldung.dependencyinjections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebsiteControllerConstructorInjection {

    private HomepageService homeService;

    @Autowired
    public WebsiteControllerConstructorInjection(HomepageService homeService) {
        this.homeService = homeService;
    }

    public String getContentsViaConstructorInjection() {
        return homeService.getContents();
    }

}
