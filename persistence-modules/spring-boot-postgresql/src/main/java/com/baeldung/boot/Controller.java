package com.baeldung.boot;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    AppUserRepository appUserRepository;

    @GetMapping("/next")
    public AppUser getNextUser() {
        AppUser appUser = new AppUser();
        return appUserRepository.save(appUser);
    }

    @GetMapping("/find/{id}")
    public AppUser get(@PathVariable(name = "id") Integer id) {
        Optional<AppUser> emp = appUserRepository.findById(id);
        return emp.orElse(null);
    }

}
