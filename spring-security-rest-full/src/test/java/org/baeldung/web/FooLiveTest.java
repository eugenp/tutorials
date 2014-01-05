package org.baeldung.web;

import org.baeldung.common.web.AbstractLiveTest;
import org.baeldung.persistence.model.Foo;

public class FooLiveTest extends AbstractLiveTest<Foo> {

    public FooLiveTest() {
        super(Foo.class);
    }

}
