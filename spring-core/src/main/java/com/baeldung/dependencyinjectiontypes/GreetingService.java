package com.baeldung.dependencyinjectiontypes;

import java.util.Collection;

public class GreetingService {

    private GreetingDao greetingDao;

    public GreetingService(GreetingDao greetingDao) {
        this.greetingDao = greetingDao;
    }

    public Collection<Object> getGreetings() {
        return greetingDao.getGreetings();
    }

}
