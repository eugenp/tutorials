package com.baeldung.scholarshipapproval.domain.service;

import com.baeldung.scholarshipapproval.database.repository.DatabaseRepositoryPort;
import com.baeldung.scholarshipapproval.email.ScholarshipsEmailPort;
import com.baeldung.scholarshipapproval.reports.ScholarshipProcessRunReportPort;
import com.baeldung.scholarshipapproval.domain.model.Applicant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@Service("scholarshipApprovalService")
public class ScholarshipApprovalServiceImpl implements ScholarshipApprovalService {
    @Autowired
    private DatabaseRepositoryPort databaseRepositoryPort;

    @Autowired
    private ScholarshipsEmailPort emailPort;

    @Autowired
    private ScholarshipProcessRunReportPort reportsPort;

    private LocalDate DATE_FROM = LocalDate.of(2018, Month.DECEMBER, 1);
    private LocalDate DATE_TO = LocalDate.of(2018, Month.DECEMBER, 31);
    private Integer MIN_MARKS_THRESHOLD = 95;
    private Double MAX_HOUSEHOLD_INCOME_THRESHOLD = 25000D;


    @Override
    public List<Applicant> runScholarshipDecisionProcess() {
        List<Applicant> applicants = databaseRepositoryPort.retrieveApplicants();

        List<Applicant> successfulApplicants = applicants.stream()
                .filter(applicant ->
                        ((applicant.getApplicationDate().isBefore(DATE_TO)
                        && applicant.getApplicationDate().isAfter(DATE_FROM))
                        && applicant.getMarks() >= MIN_MARKS_THRESHOLD)
                        && applicant.getHouseholdIncome() <= MAX_HOUSEHOLD_INCOME_THRESHOLD)
                .peek(successfulApplicant -> successfulApplicant.setScholarshipApproved(true))
                .collect(Collectors.toList());

        return successfulApplicants;
    }

    @Override
    public void emailSuccessfulApplicants(List<Applicant> successfulApplicants) {
        emailPort.emailSuccessfulApplicants(successfulApplicants);
    }

    @Override
    public void storeScholarshipProcessResultReport(List<Applicant> successfulApplicants) {
        reportsPort.saveReport(successfulApplicants);
    }
}
