package com.baeldung.hexagonal_architecture.ports;

import com.baeldung.hexagonal_architecture.domain.*;

public interface ReportPublisher {
    void publishStudentReport(StudentReport studentFinalReport);
}
