package com.baeldung.hibernate.union.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.baeldung.hibernate.union.dto.PersonDto;
import com.baeldung.hibernate.union.model.Lecturer;
import com.baeldung.hibernate.union.model.Researcher;
import com.baeldung.hibernate.union.repository.LecturerRepository;
import com.baeldung.hibernate.union.repository.ResearcherRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Service
public class UnionService {

    @PersistenceContext
    private EntityManager em;

    private LecturerRepository lecturerRepository;

    private ResearcherRepository researcherRepository;

    public UnionService(LecturerRepository lecturerRepository, ResearcherRepository researcherRepository) {
        this.lecturerRepository = lecturerRepository;
        this.researcherRepository = researcherRepository;
    }

    public List<PersonDto> fetch() {
        return em.createQuery("""
            select new PersonDto(l.id, l.name) from Lecturer l
            union
            select new PersonDto(r.id, r.name) from Researcher r
            """, PersonDto.class)
            .getResultList();
    }

    public List<PersonDto> fetchAll() {
        return em.createQuery("""
            select new PersonDto(l.id, l.name) from Lecturer l
            union all
            select new PersonDto(r.id, r.name) from Researcher r
            """, PersonDto.class)
            .getResultList();
    }

    public List<PersonDto> fetchWithDiscriminator() {
        return em.createQuery("""
            select new PersonDto(l.id, l.name, 'LECTURER') from Lecturer l
            union
            select new PersonDto(r.id, r.name, 'RESEARCHER') from Researcher r
            """, PersonDto.class)
            .getResultList();
    }

    public List<PersonDto> fetchManually() {
        List<Lecturer> lecturers = lecturerRepository.findAll();
        List<Researcher> researchers = researcherRepository.findAll();

        return Stream.concat(lecturers.stream()
            .map(e -> new PersonDto(e.getId(), e.getName())),
            researchers.stream()
                .map(c -> new PersonDto(c.getId(), c.getName())))
            .toList();
    }

    public Set<PersonDto> fetchSetManually() {
        List<Lecturer> lecturers = lecturerRepository.findAll();
        List<Researcher> researchers = researcherRepository.findAll();

        return Stream.concat(lecturers.stream()
            .map(e -> new PersonDto(e.getId(), e.getName())),
            researchers.stream()
                .map(c -> new PersonDto(c.getId(), c.getName())))
            .collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    public List<PersonDto> fetchView() {
        return em.createNativeQuery("select e.id, e.name, e.role from person_view e", PersonDto.class)
            .getResultList();
    }

    public List<PersonDto> fetchWithCriteria() {
        var session = em.unwrap(Session.class);
        var builder = session.getCriteriaBuilder();

        CriteriaQuery<PersonDto> lecturerQuery = builder.createQuery(PersonDto.class);
        Root<Lecturer> lecturer = lecturerQuery.from(Lecturer.class);
        lecturerQuery.select(builder.construct(PersonDto.class, lecturer.get("id"), lecturer.get("name"), builder.literal("LECTURER")));

        CriteriaQuery<PersonDto> researcherQuery = builder.createQuery(PersonDto.class);
        Root<Researcher> researcher = researcherQuery.from(Researcher.class);
        researcherQuery.select(builder.construct(PersonDto.class, researcher.get("id"), researcher.get("name"), builder.literal("RESEARCHER")));

        var unionQuery = builder.unionAll(lecturerQuery, researcherQuery);

        return session.createQuery(unionQuery)
            .getResultList();
    }
}
