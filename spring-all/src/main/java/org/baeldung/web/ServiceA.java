package org.baeldung.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServiceA implements IServiceA {

    @Autowired
    private ServiceB serviceB;

    public ServiceA() {
        super();
    }

    //

    public void testA() {
        System.out.println();
    }

}
