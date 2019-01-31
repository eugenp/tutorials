package com.baeldung.scholarshipapproval.reports;

import com.baeldung.scholarshipapproval.domain.model.Applicant;

import java.util.List;

public class ScholarshipProcessRunReportExcelAdapter implements ScholarshipProcessRunReportPort {
    @Override
    public void saveReport(List<Applicant> applicants) {
        //saves the applicants data to an excel file
    }
}
