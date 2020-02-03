package com.baeldung.hexagonal.domain;

import java.io.Serializable;


public interface IValueObject extends Serializable {
    String getName();
}
