package org.baeldung.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServiceB {

    @Autowired
    private IServiceA serviceA;

    public ServiceB() {
        super();
    }

    //

    public void testB() {
        System.out.println();
    }

}
