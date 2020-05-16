package com.baeldung.service;

import com.baeldung.web.dto.Foo;

public interface IFooService {

    Foo findOne(final Long id);

}
