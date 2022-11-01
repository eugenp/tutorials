package com.baeldung.controller;

import com.baeldung.model.Credential;
import com.baeldung.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.credhub.support.CredentialPermission;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CredentialController {
    @Autowired
    CredentialService credentialService;

    @PostMapping("/credentials")
    public ResponseEntity<String> generatePassword(@RequestBody String credentialName) {
        return new ResponseEntity<>(credentialService.generatePassword(credentialName), HttpStatus.OK);
    }

    @PutMapping("/credentials")
    public ResponseEntity<String> writeJSONCredential(@RequestBody Credential secret) {
        return new ResponseEntity<>(credentialService.writeCredential(secret), HttpStatus.OK);
    }

    @PostMapping("/credentials/rotate")
    public ResponseEntity<String> rotatePassword(@RequestBody String credentialName) {
        return new ResponseEntity<>(credentialService.rotatePassword(credentialName), HttpStatus.OK);
    }

    @DeleteMapping("/credentials")
    public ResponseEntity<String> deletePassword(@RequestBody String credentialName) {
        return new ResponseEntity<>(credentialService.deletePassword(credentialName), HttpStatus.OK);
    }

    @GetMapping("/credentials")
    public ResponseEntity<String> getPassword(@RequestBody String credentialName) {
        return new ResponseEntity<>(credentialService.getPassword(credentialName), HttpStatus.OK);
    }

    @PostMapping("/permissions")
    public ResponseEntity<CredentialPermission> addPermission(@RequestBody String credentialName) {
        return new ResponseEntity<>(credentialService.addCredentialPermission(credentialName), HttpStatus.OK);
    }

    @GetMapping("/permissions")
    public ResponseEntity<CredentialPermission> getPermission(@RequestBody String credentialName) {
        return new ResponseEntity<>(credentialService.getCredentialPermission(credentialName), HttpStatus.OK);
    }
}
