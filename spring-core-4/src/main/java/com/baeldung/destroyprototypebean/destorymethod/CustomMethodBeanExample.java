package com.baeldung.destroyprototypebean.destorymethod;

public class CustomMethodBeanExample {

    private boolean destroyed = false;

    public void destroy() {
        // release all resources that the bean is holding
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

}
