package org.baeldung.web;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServiceB implements IServiceB {

    public ServiceB() {
        super();
    }

    //

    public void testB() {
        System.out.println();
    }

}
