package com.baeldung.creational.abstractfactory;

public interface AbstractFactory<T> {
    T create(String type) ;
}