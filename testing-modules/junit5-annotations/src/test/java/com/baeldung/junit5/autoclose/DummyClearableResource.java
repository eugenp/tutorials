package com.baeldung.junit5.autoclose;

class DummyClearableResource {

    private boolean cleared = false;

    public void clear() {
        System.out.println("Clear Dummy Resource");
        cleared = true;
    }

    public boolean isClear() {
        return cleared;
    }
}
