package com.baeldung.hexagonal.ports.driver;

public interface IModify<INPUT, OUTPUT> {

    OUTPUT execute(INPUT params);

}
