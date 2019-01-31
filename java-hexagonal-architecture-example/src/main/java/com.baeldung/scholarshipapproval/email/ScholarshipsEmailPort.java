package com.baeldung.scholarshipapproval.email;

import com.baeldung.scholarshipapproval.domain.model.Applicant;

import java.util.List;

public interface ScholarshipsEmailPort {
    /**
     * Email successful applicants.
     * @param successfulApplicants
     */
    void emailSuccessfulApplicants(List<Applicant> successfulApplicants);
}
