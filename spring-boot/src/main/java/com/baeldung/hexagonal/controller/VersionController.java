package com.baeldung.hexagonal.controller;

import com.baeldung.hexagonal.model.Version;
import com.baeldung.hexagonal.repository.VersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
public class VersionController implements IVersionController {

    @Autowired
    VersionRepository versionRepository;

    @Override
    @GetMapping("/{appName:.+}")
    public String getVersion(@PathVariable final String appName) {
        return versionRepository.findById(appName)
                .map(Version::getAppName)
                .orElse("No app found with name" + appName);
    }

}
