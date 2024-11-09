package com.baeldung.thirdpartylib.disableautowiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestBean {

    @Autowired
    private TestDependency testDependency;
}
