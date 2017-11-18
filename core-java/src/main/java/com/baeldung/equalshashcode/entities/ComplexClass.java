package com.baeldung.equalshashcode.entities;

import java.util.List;
import java.util.Set;

public class ComplexClass {

    private List<?> genericList;
    private Set<Integer> integerSet;

    public ComplexClass(List<?> genericArrayList, Set<Integer> integerHashSet) {
        super();
        this.genericList = genericArrayList;
        this.integerSet = integerHashSet;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((genericList == null) ? 0 : genericList.hashCode());
        result = prime * result + ((integerSet == null) ? 0 : integerSet.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof ComplexClass))
            return false;
        ComplexClass other = (ComplexClass) obj;
        if (genericList == null) {
            if (other.genericList != null)
                return false;
        } else if (!genericList.equals(other.genericList))
            return false;
        if (integerSet == null) {
            if (other.integerSet != null)
                return false;
        } else if (!integerSet.equals(other.integerSet))
            return false;
        return true;
    }

    protected List<?> getGenericList() {
        return genericList;
    }

    protected void setGenericArrayList(List<?> genericList) {
        this.genericList = genericList;
    }

    protected Set<Integer> getIntegerSet() {
        return integerSet;
    }

    protected void setIntegerSet(Set<Integer> integerSet) {
        this.integerSet = integerSet;
    }
}
