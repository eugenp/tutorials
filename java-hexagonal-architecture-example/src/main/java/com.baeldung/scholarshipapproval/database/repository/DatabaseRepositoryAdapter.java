package com.baeldung.scholarshipapproval.database.repository;

import com.baeldung.scholarshipapproval.database.model.Applicant;

import java.util.ArrayList;
import java.util.List;

public class DatabaseRepositoryAdapter implements DatabaseRepositoryPort {
    @Override
    public List<com.baeldung.scholarshipapproval.domain.model.Applicant> retrieveApplicants() {
        return convertToDomain(retrieveAllApplicantsFromDatabase());
    }

    /**
     * Fetches all of the applicants from the database.
     * @return
     */
    private List<Applicant> retrieveAllApplicantsFromDatabase()  {
        List<Applicant> applicantsFromDatabase = null;
        //applicantsFromDatabase = retrieve from database with the help of hibernate session

        return applicantsFromDatabase;
    }

    /**
     * Converts a list of {@link Applicant} JPA entities
     * to a list of {@link com.baeldung.scholarshipapproval.domain.model.Applicant} domain objects.
     * @param applicantsFromDatabase
     * @return
     */
    private List<com.baeldung.scholarshipapproval.domain.model.Applicant> convertToDomain(List<Applicant> applicantsFromDatabase) {
        //conversion logic from JPA entities to domain objects goes here.
        return new ArrayList<>();
    }
}
