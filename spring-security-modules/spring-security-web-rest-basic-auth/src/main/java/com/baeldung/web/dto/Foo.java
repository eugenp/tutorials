package com.baeldung.web.dto;

import java.io.Serializable;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Foo implements Serializable {

    public Foo() {
        super();
    }

}
