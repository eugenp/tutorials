package com.baeldung.tieredcompilation;

public interface Formatter {

    <T> String format(T object) throws Exception;

}
