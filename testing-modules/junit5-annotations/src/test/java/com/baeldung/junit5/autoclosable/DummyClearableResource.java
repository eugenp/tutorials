package com.baeldung.junit5.autoclosable;

class DummyClearableResource {

    private boolean clear = false;

    public void clear() {
        System.out.println("Clear Dummy Resource");
        clear = true;
    }

    public boolean isClear() {
        return clear;
    }
}
