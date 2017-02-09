package com.baeldung.web.rest;

import com.baeldung.config.DefaultProfileUtil;
import com.baeldung.config.JHipsterProperties;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController @RequestMapping("/api") public class ProfileInfoResource {

    @Inject Environment env;

    @Inject private JHipsterProperties jHipsterProperties;

    @RequestMapping(value = "/profile-info",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE) public ProfileInfoResponse getActiveProfiles() {
        return new ProfileInfoResponse(DefaultProfileUtil.getActiveProfiles(env), getRibbonEnv());
    }

    private String getRibbonEnv() {
        String[] activeProfiles = DefaultProfileUtil.getActiveProfiles(env);
        String[] displayOnActiveProfiles = jHipsterProperties.getRibbon().getDisplayOnActiveProfiles();

        if (displayOnActiveProfiles == null) {
            return null;
        }

        List<String> ribbonProfiles = new ArrayList<>(Arrays.asList(displayOnActiveProfiles));
        List<String> springBootProfiles = Arrays.asList(activeProfiles);
        ribbonProfiles.retainAll(springBootProfiles);

        if (ribbonProfiles.size() > 0) {
            return ribbonProfiles.get(0);
        }
        return null;
    }

    class ProfileInfoResponse {

        public String[] activeProfiles;
        public String ribbonEnv;

        ProfileInfoResponse(String[] activeProfiles, String ribbonEnv) {
            this.activeProfiles = activeProfiles;
            this.ribbonEnv = ribbonEnv;
        }
    }
}
