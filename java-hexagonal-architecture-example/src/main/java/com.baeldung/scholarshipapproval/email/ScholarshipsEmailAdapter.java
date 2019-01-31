package com.baeldung.scholarshipapproval.email;

import com.baeldung.scholarshipapproval.domain.model.Applicant;

import java.util.List;

public class ScholarshipsEmailAdapter implements ScholarshipsEmailPort {
    @Override
    public void emailSuccessfulApplicants(List<Applicant> successfulApplicants) {
        //email server specific email-sending code
    }
}
