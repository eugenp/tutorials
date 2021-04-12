package com.baeldung.hexagonalarchitecture1.recordscheduler.adapter.out.authorize;

import com.baeldung.hexagonalarchitecture1.recordscheduler.application.port.out.AuthorizeRecordRequest;
import com.baeldung.hexagonalarchitecture1.recordscheduler.domain.RecordSchedule;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthorizationAdapter implements AuthorizeRecordRequest {

    @Override
    public boolean isRecordingAllowed(RecordSchedule recordSchedule) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<RecordSchedule> request = new HttpEntity<>(recordSchedule);
        Boolean foo = restTemplate.postForObject("http://localhost:8080/authorize", request, Boolean.class);
        return foo == null || foo;
    }
}
