package com.baeldung.examples.common;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class AccountServiceImpl implements AccountService {

    public List<String> listAccountTypes() {
        return Arrays.asList("Checking", "Saving");
    }

}
