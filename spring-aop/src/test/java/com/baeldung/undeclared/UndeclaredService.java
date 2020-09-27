package com.baeldung.undeclared;

import org.springframework.stereotype.Service;

@Service
public class UndeclaredService {

    @ThrowUndeclared
    public void doSomething() {}
}
