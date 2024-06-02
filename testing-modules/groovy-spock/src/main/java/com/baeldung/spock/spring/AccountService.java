package com.baeldung.spock.spring;

import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final DataProvider provider;

    public AccountService(final DataProvider provider) {
        this.provider = provider;
    }

    public String getData(final String param) {
        return "Fetched: " + provider.fetchData(param);
    }

}
