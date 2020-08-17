package com.baeldung.lob;

interface Storage {

    String save(String fileName, byte[] fileContent);

    String uri();
}
