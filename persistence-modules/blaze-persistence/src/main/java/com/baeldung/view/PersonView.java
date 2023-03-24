package com.baeldung.view;

import com.baeldung.model.Person;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;

@EntityView(Person.class)
public interface PersonView {

    @IdMapping
    Long getId();

    int getAge();

    String getName();
}
