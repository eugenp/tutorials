package com.baeldung.removingroleprefix;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestSecuredController {

    @GetMapping("/test-resource")
    public ResponseEntity<String> testAdmin() {
        return ResponseEntity.ok("GET request successful");
    }

    @PreAuthorize("hasAuthority('ADMINISTRATION')")
    @GetMapping("/test-resource-method-security-with-authorities-resource")
    public ResponseEntity<String> testAdminAuthority() {
        return ResponseEntity.ok("GET request successful");
    }

    @RolesAllowed({"ADMIN"})
    @GetMapping("/test-resource-method-security-resource")
    public ResponseEntity<String> testAdminRole() {
        return ResponseEntity.ok("GET request successful");
    }
}
