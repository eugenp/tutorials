package org.baeldung.boot.domain;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
public class Foo extends AbstractEntity {

    private String name;
    
    public Foo(long id) {
        super(id);
        name = randomAlphanumeric(4);
    }

    public Foo(long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Foo [name=" + name + ", id=" + id + "]";
    }
    
}
