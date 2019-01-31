package com.baeldung.scholarshipapproval.domain.service;

import com.baeldung.scholarshipapproval.domain.model.Applicant;

import java.util.List;

public interface ScholarshipApprovalService {
    /**
     * Runs a scholarship decision process.
     * @return
     */
    List<Applicant> runScholarshipDecisionProcess();

    /**
     * Emails the successful applicants about the scholarship approval on their application.
     * @param successfulApplicants
     */
    void emailSuccessfulApplicants(List<Applicant> successfulApplicants);

    /**
     * Stores the scholarship process result report of successful applicants.
     * @param successfulApplicants
     */
    void storeScholarshipProcessResultReport(List<Applicant> successfulApplicants);
}
