package org.baeldung.boot.domain;

public class Bar extends AbstractEntity {

    private int value;
    
    public Bar(long id) {
        super(id);
    }

    public Bar(long id, int value) {
        super(id);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Bar [value=" + value + ", id=" + id + "]";
    }
    
}
