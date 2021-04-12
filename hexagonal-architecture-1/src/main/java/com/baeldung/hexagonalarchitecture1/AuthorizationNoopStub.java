package com.baeldung.hexagonalarchitecture1;

import com.baeldung.hexagonalarchitecture1.recordscheduler.domain.RecordSchedule;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationNoopStub {


    @PostMapping("/authorize")
    Boolean validateRequest(@RequestBody RecordSchedule recordSchedule) {
        return true;
    }
}
