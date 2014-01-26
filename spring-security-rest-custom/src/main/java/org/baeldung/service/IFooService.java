package org.baeldung.service;

import org.baeldung.web.dto.Foo;

public interface IFooService {

    Foo findOne(final Long id);

}
