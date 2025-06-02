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
import com.baeldung.hibernate.union.model.Lecturer;
import com.baeldung.hibernate.union.model.PersonView;
import com.baeldung.hibernate.union.model.Researcher;
import com.baeldung.hibernate.union.repository.LecturerRepository;
import com.baeldung.hibernate.union.repository.ResearcherRepository;
import com.baeldung.hibernate.union.service.UnionService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
@Sql(statements = { "CREATE VIEW IF NOT EXISTS person_view AS SELECT id, name, 'LECTURER' AS role FROM Lecturer UNION SELECT id, name, 'RESEARCHER' AS role FROM Researcher" })
class UnionServiceIntegrationTest {

    private static final int COUNT_UNION = 5;
    private static final int COUNT_UNION_ALL = 6;
    
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private ResearcherRepository researcherRepository;

    @Autowired
    private UnionService unionService;

    @BeforeEach
    void setUp() {
        lecturerRepository.saveAll(List.of(new Lecturer(1l, "Alice"), new Lecturer(2l, "Bob"), new Lecturer(3l, "Candace")));
        researcherRepository.saveAll(List.of(new Researcher(3l, "Candace"), new Researcher(4l, "Diana"), new Researcher(5l, "Elena")));
    }

    @Test
    void whenUnionQuery_thenUnifiedResult() {
        List<PersonDto> result = unionService.fetch();

        assertEquals(COUNT_UNION, result.size());
    }

    @Test
    void whenUnionAllQuery_thenUnifiedResult() {
        List<PersonDto> result = unionService.fetchAll();

        assertEquals(COUNT_UNION_ALL, result.size());
    }

    @Test
    void whenUnionWithDiscriminatorColumnQuery_thenUnifiedResult() {
        List<PersonDto> result = unionService.fetchWithDiscriminator();

        assertEquals(COUNT_UNION_ALL, result.size());
    }

    @Test
    void whenMergedInMemoryList_thenUnifiedResult() {
        List<PersonDto> result = unionService.fetchManually();

        assertEquals(COUNT_UNION_ALL, result.size());
    }

    @Test
    void whenMergedInMemorySet_thenUnifiedResult() {
        Set<PersonDto> result = unionService.fetchSetManually();

        assertEquals(COUNT_UNION, result.size());
    }

    @Test
    void givenView_whenFetchAll_thenUnifiedResult() {
        List<PersonDto> results = unionService.fetchView();

        assertEquals(COUNT_UNION_ALL, results.size());
    }

    @Test
    void givenView_whenFetchWithInterface_thenUnifiedResult() {
        List<PersonView> results = lecturerRepository.findPersonView();

        assertEquals(COUNT_UNION_ALL, results.size());
    }

    @Test
    void whenFetchWithCriteria_thenReturnAllPeople() {
        List<PersonDto> results = unionService.fetchWithCriteria();

        assertEquals(COUNT_UNION_ALL, results.size());
    }
}
