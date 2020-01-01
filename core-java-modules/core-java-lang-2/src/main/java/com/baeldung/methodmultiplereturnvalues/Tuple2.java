package com.baeldung.methodmultiplereturnvalues;

public class Tuple2 <K, V> {

    private K first;
    private V second;
    
    public Tuple2() {}
    
    public Tuple2(K first, V second) {
        this.first = first;
        this.second = second;
    }

    public K getFirst() {
        return first;
    }
    
    public V getSecond() {
        return second;
    }

    public void setFirst(K first) {
        this.first = first;
    }

    public void setSecond(V second) {
        this.second = second;
    }

}