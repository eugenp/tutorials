package com.baeldung.hexagonal_architecture.domain;

import java.util.List;

import com.baeldung.hexagonal_architecture.utils.*;
import com.baeldung.hexagonal_architecture.adapters.ReportAnnouncementPortal;
import com.baeldung.hexagonal_architecture.ports.*;

public class StudentReport {

    private Long reportID;
    private Student student;
    private List<Subject> subjects;
    private ReportPublisher studentReportPublisher;
    private ResultStatus reportResultStatus;

    {
        studentReportPublisher = new ReportAnnouncementPortal();
    }

    public Long getReportID() {
        return reportID;
    }

    public void setReportID(Long reportID) {
        this.reportID = reportID;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public void publishStudentReport() {
        studentReportPublisher.publishStudentReport(this);
    }

    public ResultStatus getReportResultStatus() {
        return reportResultStatus;
    }

    public void setReportResultStatus(ResultStatus reportResultStatus) {
        this.reportResultStatus = reportResultStatus;
    }

}
