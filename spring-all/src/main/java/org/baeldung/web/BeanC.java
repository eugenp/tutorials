package org.baeldung.web;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class BeanC implements IBeanC {

    public BeanC() {
        super();
    }

}
