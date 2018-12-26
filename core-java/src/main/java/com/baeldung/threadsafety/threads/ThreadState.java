package com.baeldung.threadsafety.threads;

import com.baeldung.threadsafety.services.StateHolder;

public class ThreadState {
    
    public static final ThreadLocal<StateHolder> statePerThread = new ThreadLocal<StateHolder>() {
        
        @Override
        protected StateHolder initialValue() {
            return new StateHolder("active");  
        }
    };
}
