package com.baeldung.copy.shallow;

import java.io.Serializable;
import java.util.Objects;

public class Founder implements Cloneable, Serializable {

    private String name;
    private Integer year;

    public Founder(String name, Integer year) {
        this.name = name;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Founder founder = (Founder) o;
        return (int) year == founder.year && Objects.equals(name, founder.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year);
    }

    @Override
    public Founder clone() {
        try {
            // nothing special here since fields in this class are immutable
            return (Founder) super.clone();
        } catch (CloneNotSupportedException e) {
            // shouldn't happen since we're Cloneable
            throw new RuntimeException(e);
        }
    }
}
