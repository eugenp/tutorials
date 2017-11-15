package com.baeldung.concurrent.daemon;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

<<<<<<< HEAD
=======
import org.junit.Ignore;
>>>>>>> d54917c7e9f0f74c40982571af8ac9f61782b7cb
import org.junit.Test;

public class DaemonThreadTest {

    @Test
    @Ignore
    public void whenCallIsDaemon_thenCorrect() {
        NewThread daemonThread = new NewThread();
        NewThread userThread = new NewThread();
        daemonThread.setDaemon(true);
        daemonThread.start();
        userThread.start();

        assertTrue(daemonThread.isDaemon());
        assertFalse(userThread.isDaemon());
    }

    @Test(expected = IllegalThreadStateException.class)
    @Ignore
    public void givenUserThread_whenSetDaemonWhileRunning_thenIllegalThreadStateException() {
        NewThread daemonThread = new NewThread();
        daemonThread.start();
        daemonThread.setDaemon(true);
    }
}
