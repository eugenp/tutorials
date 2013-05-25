package org.baeldung.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServiceA {

    @Autowired
    public ServiceA(final IServiceB serviceB) {
        super();
    }

    //

    public void testA() {
        System.out.println();
    }

}
