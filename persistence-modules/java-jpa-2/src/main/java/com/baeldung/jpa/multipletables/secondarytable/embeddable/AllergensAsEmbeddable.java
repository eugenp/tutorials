package com.baeldung.jpa.multipletables.secondarytable.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AllergensAsEmbeddable {

    @Column(name = "peanuts", table = "allergens")
    private boolean peanuts;

    @Column(name = "celery", table = "allergens")
    private boolean celery;

    @Column(name = "sesame_seeds", table = "allergens")
    private boolean sesameSeeds;

    public boolean isPeanuts() {
        return peanuts;
    }

    public void setPeanuts(boolean peanuts) {
        this.peanuts = peanuts;
    }

    public boolean isCelery() {
        return celery;
    }

    public void setCelery(boolean celery) {
        this.celery = celery;
    }

    public boolean isSesameSeeds() {
        return sesameSeeds;
    }

    public void setSesameSeeds(boolean sesameSeeds) {
        this.sesameSeeds = sesameSeeds;
    }

    @Override
    public String toString() {
        return "AllergensAsEmbeddable [peanuts=" + peanuts + ", celery=" + celery + ", sesameSeeds=" + sesameSeeds + "]";
    }

}
