package com.baeldung.scholarshipapproval.database.repository;

import com.baeldung.scholarshipapproval.domain.model.Applicant;

import java.util.List;

public interface DatabaseRepositoryPort {
    /**
     * Fetches all of the applicants.
     * @return
     */
    List<Applicant> retrieveApplicants();
}
