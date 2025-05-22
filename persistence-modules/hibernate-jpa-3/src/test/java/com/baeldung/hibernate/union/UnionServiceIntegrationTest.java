package com.baeldung.hibernate.union;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import com.baeldung.hibernate.union.dto.PersonDto;
import com.baeldung.hibernate.union.dto.PersonView;
import com.baeldung.hibernate.union.model.Contractor;
import com.baeldung.hibernate.union.model.Employer;
import com.baeldung.hibernate.union.repository.ContractorRepository;
import com.baeldung.hibernate.union.repository.EmployerRepository;
import com.baeldung.hibernate.union.service.UnionService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
@Sql(statements = { "CREATE VIEW IF NOT EXISTS person_view AS SELECT id, name, 'EMPLOYER' AS entity FROM Employer UNION SELECT id, name, 'CONTRACTOR' AS entity FROM Contractor" })
class UnionServiceIntegrationTest {

    private static final int COUNT_UNION = 5;
    private static final int COUNT_UNION_ALL = 6;
    
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private ContractorRepository contractorRepository;

    @Autowired
    private UnionService unionService;

    @BeforeEach
    void setUp() {
        employerRepository.saveAll(List.of(new Employer(1l, "Alice"), new Employer(2l, "Bob"), new Employer(3l, "Candace")));
        contractorRepository.saveAll(List.of(new Contractor(3l, "Candace"), new Contractor(4l, "Diana"), new Contractor(5l, "Elena")));
    }

    @Test
    void givenEmployersAndContractors_whenUnionQuery_thenUnifiedResult() {
        List<PersonDto> result = unionService.fetch();

        assertEquals(COUNT_UNION, result.size());
    }

    @Test
    void givenEmployersAndContractors_whenUnionAllQuery_thenUnifiedResult() {
        List<PersonDto> result = unionService.fetchAll();

        assertEquals(COUNT_UNION_ALL, result.size());
    }

    @Test
    void givenEmployersAndContractors_whenUnionWithDiscriminatorColumnQuery_thenUnifiedResult() {
        List<PersonDto> result = unionService.fetchWithDiscriminator();

        assertEquals(COUNT_UNION_ALL, result.size());
    }

    @Test
    void givenSeparateQueries_whenMergedInMemoryList_thenUnifiedResult() {
        List<PersonDto> result = unionService.fetchManually();

        assertEquals(COUNT_UNION_ALL, result.size());
    }

    @Test
    void givenSeparateQueries_whenMergedInMemorySet_thenUnifiedResult() {
        Set<PersonDto> result = unionService.fetchSetManually();

        assertEquals(COUNT_UNION, result.size());
    }

    @Test
    void givenView_whenFetchAll_thenUnifiedResult() {

        @SuppressWarnings("unchecked")
        List<PersonDto> results = em.createNativeQuery("""
            select e.id, e.name, e.entity from person_view e
            """, PersonDto.class)
            .getResultList();

        assertEquals(COUNT_UNION_ALL, results.size());
    }

    @Test
    void givenView_whenFetchWithInterface_thenUnifiedResult() {

        List<PersonView> results = employerRepository.findPersonView();

        assertEquals(COUNT_UNION_ALL, results.size());
    }
}
