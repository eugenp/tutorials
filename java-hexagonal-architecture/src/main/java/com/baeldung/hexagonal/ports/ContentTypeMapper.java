package com.baeldung.hexagonal.ports;

public interface ContentTypeMapper<T> {

        T mapContent(String content);
}
