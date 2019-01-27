package org.baeldung.sampleabstract;

import org.springframework.stereotype.Component;

@Component
public class FooGenericService extends AbstractGenericService<FooBean, BarBean> {

}
