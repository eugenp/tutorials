package io.jsonwebtoken.jjwtfun.controller;

import io.jsonwebtoken.jjwtfun.service.SecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class SecretsController extends BaseController {

    @Autowired
    SecretService secretService;

    @RequestMapping(value = "/get-secrets", method = GET)
    public Map<String, String> getSecrets() {
        return secretService.getSecrets();
    }

    @RequestMapping(value = "/refresh-secrets", method = GET)
    public Map<String, String> refreshSecrets() {
        return secretService.refreshSecrets();
    }

    @RequestMapping(value = "/set-secrets", method = POST)
    public Map<String, String> setSecrets(@RequestBody Map<String, String> secrets) {
        secretService.setSecrets(secrets);
        return secretService.getSecrets();
    }
}
