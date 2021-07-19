package com.baeldung.archunit.smurfs.persistence.domain;

public class Smurf {
    private String name;
    private boolean comic;
    private boolean cartoon;

    public Smurf(String name, boolean comic, boolean cartoon) {
        this.name = name;
        this.comic = comic;
        this.cartoon = cartoon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isComic() {
        return comic;
    }

    public void setCommic(boolean comic) {
        this.comic = comic;
    }

    public boolean isCartoon() {
        return cartoon;
    }

    public void setCartoon(boolean cartoon) {
        this.cartoon = cartoon;
    }
}
