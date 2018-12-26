package com.baeldung.threadsafety.threads;

import com.baeldung.threadsafety.services.StateHolder;

public class ThreadC extends Thread {
    
    @Override
    public void run() {
        StateHolder stateHolder = ThreadState.statePerThread.get();
        System.out.println(stateHolder);
    }
}
