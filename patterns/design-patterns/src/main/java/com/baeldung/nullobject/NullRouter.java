package com.baeldung.nullobject;

public class NullRouter implements Router {

    @Override
    public void route(Message msg) {
        // routing to /dev/null
    }

}
