package com.baeldung.health;

import org.springframework.boot.actuate.health.HttpCodeStatusMapper;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class CustomStatusCodeMapper implements HttpCodeStatusMapper {

    @Override
    public int getStatusCode(Status status) {
        if (status == Status.DOWN) {
            return 500;
        }

        if (status == Status.OUT_OF_SERVICE) {
            return 503;
        }

        if (status == Status.UNKNOWN) {
            return 500;
        }

        if (status.getCode().equals("WARNING")) {
            return 500;
        }

        return 200;
    }
}
