package com.baeldung.hibernate.union.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.baeldung.hibernate.union.dto.PersonDto;
import com.baeldung.hibernate.union.model.Contractor;
import com.baeldung.hibernate.union.model.Employer;
import com.baeldung.hibernate.union.repository.ContractorRepository;
import com.baeldung.hibernate.union.repository.EmployerRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Service
public class UnionService {

    @PersistenceContext
    private EntityManager em;

    private EmployerRepository employerRepository;

    private ContractorRepository contractorRepository;

    public UnionService(EmployerRepository employerRepository, ContractorRepository contractorRepository) {
        this.employerRepository = employerRepository;
        this.contractorRepository = contractorRepository;
    }

    public List<PersonDto> fetch() {
        return em.createQuery("""
            select new PersonDto(e.id, e.name) from Employer e
            union
            select new PersonDto(c.id, c.name) from Contractor c
            """, PersonDto.class)
            .getResultList();
    }

    public List<PersonDto> fetchAll() {
        return em.createQuery("""
            select new PersonDto(e.id, e.name) from Employer e
            union all
            select new PersonDto(c.id, c.name) from Contractor c
            """, PersonDto.class)
            .getResultList();
    }

    public List<PersonDto> fetchWithDiscriminator() {
        return em.createQuery("""
            select new PersonDto(e.id, e.name, 'EMPLOYER') from Employer e
            union
            select new PersonDto(c.id, c.name, 'CONTRACTOR') from Contractor c
            """, PersonDto.class)
            .getResultList();
    }

    public List<PersonDto> fetchManually() {
        List<Employer> employers = employerRepository.findAll();
        List<Contractor> contractors = contractorRepository.findAll();

        return Stream.concat(employers.stream()
            .map(e -> new PersonDto(e.getId(), e.getName())),
            contractors.stream()
                .map(c -> new PersonDto(c.getId(), c.getName())))
            .toList();
    }

    public Set<PersonDto> fetchSetManually() {
        List<Employer> employers = employerRepository.findAll();
        List<Contractor> contractors = contractorRepository.findAll();

        return Stream.concat(employers.stream()
            .map(e -> new PersonDto(e.getId(), e.getName())),
            contractors.stream()
                .map(c -> new PersonDto(c.getId(), c.getName())))
            .collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    public List<PersonDto> fetchView() {
        return em.createNativeQuery("select e.id, e.name, e.entity from person_view e", PersonDto.class)
            .getResultList();
    }

    public List<PersonDto> fetchWithCriteria() {
        var session = em.unwrap(Session.class);
        var builder = session.getCriteriaBuilder();

        CriteriaQuery<PersonDto> employerQuery = builder.createQuery(PersonDto.class);
        Root<Employer> employer = employerQuery.from(Employer.class);
        employerQuery.select(builder.construct(PersonDto.class, employer.get("id"), employer.get("name"), builder.literal("EMPLOYER")));

        CriteriaQuery<PersonDto> contractorQuery = builder.createQuery(PersonDto.class);
        Root<Contractor> contractor = contractorQuery.from(Contractor.class);
        contractorQuery.select(builder.construct(PersonDto.class, contractor.get("id"), contractor.get("name"), builder.literal("CONTRACTOR")));

        var unionQuery = builder.unionAll(employerQuery, contractorQuery);

        return session.createQuery(unionQuery)
            .getResultList();
    }
}
