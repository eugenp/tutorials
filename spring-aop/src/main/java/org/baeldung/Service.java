package org.baeldung;

import org.springframework.stereotype.Component;

@Component
class Service {

    @LogExecutionTime
    public void serve() throws InterruptedException {
        Thread.sleep(2000);
    }
}
