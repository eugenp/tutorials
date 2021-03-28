package com.baeldung.hexagonal_architecture.adapters;

import com.baeldung.hexagonal_architecture.ports.*;
import com.baeldung.hexagonal_architecture.domain.*;

public class ReportAnnouncementPortal implements ReportPublisher {

    @Override
    public void publishStudentReport(StudentReport studentFinalReport) {
        // student report publishing code implementations goes here...
    }
}
