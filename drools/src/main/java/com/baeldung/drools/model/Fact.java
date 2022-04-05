package com.baeldung.drools.model;

import org.kie.api.definition.type.Position;

public class Fact {

    @Position(0)
    private String element;

    @Position(1)
    private String place;

    public Fact(String element, String place) {
        this.element = element;
        this.place = place;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((element == null) ? 0 : element.hashCode());
        result = prime * result + ((place == null) ? 0 : place.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Fact other = (Fact) obj;
        if (element == null) {
            if (other.element != null)
                return false;
        } else if (!element.equals(other.element))
            return false;
        if (place == null) {
            if (other.place != null)
                return false;
        } else if (!place.equals(other.place))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Fact{" + "element='" + element + '\'' + ", place='" + place + '\'' + '}';
    }
}