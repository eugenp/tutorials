/**
 * 
 */
package com.baeldung.archunit.smurfs.persistence.domain;

/**
 * @author Philippe
 *
 */
public class Smurf {
    private String name;
    private boolean comic;
    private boolean cartoon;

    public Smurf() {}

    public Smurf(String name, boolean comic, boolean cartoon) {
        this.name = name;
        this.comic = comic;
        this.cartoon = cartoon;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the commic
     */
    public boolean isComic() {
        return comic;
    }

    /**
     * @param commic the commic to set
     */
    public void setCommic(boolean comic) {
        this.comic = comic;
    }

    /**
     * @return the cartoon
     */
    public boolean isCartoon() {
        return cartoon;
    }

    /**
     * @param cartoon the cartoon to set
     */
    public void setCartoon(boolean cartoon) {
        this.cartoon = cartoon;
    }
}
