package com.baeldung.ignite.cache;

import org.apache.ignite.IgniteException;
import org.apache.ignite.lifecycle.LifecycleBean;
import org.apache.ignite.lifecycle.LifecycleEventType;

public class CustomLifecycleBean implements LifecycleBean {
    @Override
    public void onLifecycleEvent(LifecycleEventType lifecycleEventType) throws IgniteException {
        if (lifecycleEventType == LifecycleEventType.AFTER_NODE_START) {
            //do something right after the Ignite node starts
        }
    }
}
