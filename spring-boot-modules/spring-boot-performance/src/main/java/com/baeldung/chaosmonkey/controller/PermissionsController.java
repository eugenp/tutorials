package com.baeldung.chaosmonkey.controller;

import com.baeldung.chaosmonkey.service.PermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by adi on 8/2/18.
 */
@RestController
@RequestMapping("/permissions")
public class PermissionsController {

    @Autowired private PermissionsService permissionsService;

    @GetMapping
    public List<String> getAllPermissions() {
        return permissionsService.getAllPermissions();
    }

}
