package org.baeldung.web.dto;

public class Baz {
    private long id;
    private String name;

    public Baz() {
        super();
    }

    public Baz(final long id, final String name) {
        super();

        this.id = id;
        this.name = name;
    }

    //

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}