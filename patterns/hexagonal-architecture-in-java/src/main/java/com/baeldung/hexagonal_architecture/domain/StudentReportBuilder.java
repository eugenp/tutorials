package com.baeldung.hexagonal_architecture.domain;

import java.util.List;

import com.baeldung.hexagonal_architecture.ports.ReportPublisher;
import com.baeldung.hexagonal_architecture.utils.ResultStatus;
import com.baeldung.hexagonal_architecture.utils.Subject;

public class StudentReportBuilder {

    private Long reportID;
    private Student student;
    private List<Subject> subjects;
    private ResultStatus reportResultStatus;

    public StudentReportBuilder setReportID(Long reportID) {
        this.reportID = reportID;
        return this;
    }

    public StudentReportBuilder setStudent(Student student) {
        this.student = student;
        return this;
    }

    public StudentReportBuilder setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
        return this;
    }

    public StudentReportBuilder setReportResultStatus(ResultStatus reportResultStatus) {
        this.reportResultStatus = reportResultStatus;
        return this;
    }

    public StudentReport build() {
        StudentReport studentReport = new StudentReport();
        studentReport.setReportID(reportID);
        studentReport.setReportResultStatus(reportResultStatus);
        studentReport.setStudent(student);
        studentReport.setSubjects(subjects);
        return studentReport;
    }
}
