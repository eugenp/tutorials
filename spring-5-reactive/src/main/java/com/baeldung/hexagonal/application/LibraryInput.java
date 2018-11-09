package com.baeldung.hexagonal.application;


import com.baeldung.hexagonal.doc.Port;

@Port
public interface LibraryInput {

    String getTitle();
}
