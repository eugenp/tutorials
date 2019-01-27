package org.baeldung.sampleabstract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FooService extends AbstractService {

    @Autowired
    public FooService(FooBarBean fooBarBean) {

        super(fooBarBean);
    }
}
