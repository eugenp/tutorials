package org.baeldung.boot.exceptions;

class FooNotFoundException extends RuntimeException{

    /**
     * 
     */
    private static final long serialVersionUID = 9042200028456133589L;

    public FooNotFoundException(String message){
        super(message);
    }
}
