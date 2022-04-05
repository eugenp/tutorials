/**
 * 
 */
package com.baeldung.kubernetes.admission.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.kubernetes.admission.dto.AdmissionReviewResponse;
import com.baeldung.kubernetes.admission.service.AdmissionService;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AdmissionReviewController {

    private final AdmissionService admissionService;

    @PostMapping(path = "/mutate")
    public Mono<AdmissionReviewResponse> processAdmissionReviewRequest(@RequestBody Mono<ObjectNode> request) {
        return request.map((body) -> admissionService.processAdmission(body));
    }
}
