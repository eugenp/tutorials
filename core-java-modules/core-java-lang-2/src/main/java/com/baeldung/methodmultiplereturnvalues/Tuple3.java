package com.baeldung.methodmultiplereturnvalues;

class Tuple3 <K, V, L> {

    private final K first;
    private final V second;
    private final L third;
    
    public Tuple3(K k, V v, L l){
        this.first = k;
        this.second = v;
        this.third = l;
    }

    public K getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }

    public L getThird() {
        return third;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((first == null) ? 0 : first.hashCode());
        result = prime * result + ((third == null) ? 0 : third.hashCode());
        result = prime * result + ((second == null) ? 0 : second.hashCode());
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
        Tuple3 other = (Tuple3) obj;
        if (first == null) {
            if (other.first != null)
                return false;
        } else if (!first.equals(other.first))
            return false;
        if (third == null) {
            if (other.third != null)
                return false;
        } else if (!third.equals(other.third))
            return false;
        if (second == null) {
            if (other.second != null)
                return false;
        } else if (!second.equals(other.second))
            return false;
        return true;
    }
    
}