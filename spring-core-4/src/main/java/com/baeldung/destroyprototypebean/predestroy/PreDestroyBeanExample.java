package com.baeldung.destroyprototypebean.predestroy;

import jakarta.annotation.PreDestroy;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PreDestroyBeanExample {

    private boolean destroyed = false;

    @PreDestroy
    private void destroy() {
        // release all resources that the bean is holding
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

}
