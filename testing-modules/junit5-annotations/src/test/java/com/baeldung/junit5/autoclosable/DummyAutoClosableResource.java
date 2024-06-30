package com.baeldung.junit5.autoclosable;

class DummyAutoClosableResource implements AutoCloseable {

    private boolean open = true;

    @Override
    public void close() {
        System.out.println("Closing Dummy Resource");
        open = false;
    }

    public boolean isOpen() {
        return open;
    }
}
