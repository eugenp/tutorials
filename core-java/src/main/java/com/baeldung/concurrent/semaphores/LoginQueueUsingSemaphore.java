package com.baeldung.concurrent.semaphores;

import java.util.concurrent.Semaphore;

public class LoginQueueUsingSemaphore {

    private final Semaphore semaphore;

    public LoginQueueUsingSemaphore(int slotLimit) {
        semaphore = new Semaphore(slotLimit);
    }

    public boolean tryLogin() {
        return semaphore.tryAcquire();
    }

    public void logout() {
        semaphore.release();
    }

    public int availableSlots() {
        return semaphore.availablePermits();
    }

}
