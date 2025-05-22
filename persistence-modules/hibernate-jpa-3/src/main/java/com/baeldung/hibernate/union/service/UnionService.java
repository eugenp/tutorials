package com.baeldung.hibernate.union.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.baeldung.hibernate.union.dto.PersonDto;
import com.baeldung.hibernate.union.model.Contractor;
import com.baeldung.hibernate.union.model.Employer;
import com.baeldung.hibernate.union.repository.ContractorRepository;
import com.baeldung.hibernate.union.repository.EmployerRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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
}
