package com.baeldung.primary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Gebruiker on 7/19/2018.
 */@Service
public class ManagerService {

    @Autowired
    private Manager manager;

    public Manager getManager() {
        return manager;
    }
}
