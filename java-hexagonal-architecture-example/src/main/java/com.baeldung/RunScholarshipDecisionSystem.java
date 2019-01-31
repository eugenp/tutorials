package com.baeldung;

import com.baeldung.scholarshipapproval.domain.model.Applicant;
import com.baeldung.scholarshipapproval.domain.service.ScholarshipApprovalService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RunScholarshipDecisionSystem {
    @Autowired
    private ScholarshipApprovalService scholarshipApprovalService;

    public static void main(String[] args) {
        RunScholarshipDecisionSystem run = new RunScholarshipDecisionSystem();
        run.runScholarshipDecisionSystem();
    }

    private void runScholarshipDecisionSystem() {
        List<Applicant> successfulApplicants = scholarshipApprovalService.runScholarshipDecisionProcess();
        scholarshipApprovalService.emailSuccessfulApplicants(successfulApplicants);
        scholarshipApprovalService.storeScholarshipProcessResultReport(successfulApplicants);
    }
}
