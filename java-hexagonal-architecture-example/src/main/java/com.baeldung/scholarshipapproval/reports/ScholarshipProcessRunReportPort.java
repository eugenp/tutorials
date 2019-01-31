package com.baeldung.scholarshipapproval.reports;

import com.baeldung.scholarshipapproval.domain.model.Applicant;

import java.util.List;

public interface ScholarshipProcessRunReportPort {
    /**
     * Saves the report - for all of the applicants data
     * @param applicants
     */
    void saveReport(List<Applicant> applicants);
}
