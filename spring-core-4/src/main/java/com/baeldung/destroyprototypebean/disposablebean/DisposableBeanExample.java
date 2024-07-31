package com.baeldung.destroyprototypebean.disposablebean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DisposableBeanExample implements DisposableBean {

    private boolean destroyed = false;

    @Override
    public void destroy() {
        // release all resources that the bean is holding
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

}
