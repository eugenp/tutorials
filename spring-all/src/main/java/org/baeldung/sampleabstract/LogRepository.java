package org.baeldung.sampleabstract;

import org.springframework.stereotype.Component;

@Component
public class LogRepository {

    @Override
    public String toString() {
        return "logRepository";
    }
}
