package com.baeldung.jhipster.quotes.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.baeldung.jhipster.quotes.domain.Quote;
import com.baeldung.jhipster.quotes.domain.*; // for static metamodels
import com.baeldung.jhipster.quotes.repository.QuoteRepository;
import com.baeldung.jhipster.quotes.service.dto.QuoteCriteria;
import com.baeldung.jhipster.quotes.service.dto.QuoteDTO;
import com.baeldung.jhipster.quotes.service.mapper.QuoteMapper;

/**
 * Service for executing complex queries for Quote entities in the database.
 * The main input is a {@link QuoteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QuoteDTO} or a {@link Page} of {@link QuoteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QuoteQueryService extends QueryService<Quote> {

    private final Logger log = LoggerFactory.getLogger(QuoteQueryService.class);

    private final QuoteRepository quoteRepository;

    private final QuoteMapper quoteMapper;

    public QuoteQueryService(QuoteRepository quoteRepository, QuoteMapper quoteMapper) {
        this.quoteRepository = quoteRepository;
        this.quoteMapper = quoteMapper;
    }

    /**
     * Return a {@link List} of {@link QuoteDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QuoteDTO> findByCriteria(QuoteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Quote> specification = createSpecification(criteria);
        return quoteMapper.toDto(quoteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link QuoteDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QuoteDTO> findByCriteria(QuoteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Quote> specification = createSpecification(criteria);
        return quoteRepository.findAll(specification, page)
            .map(quoteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QuoteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Quote> specification = createSpecification(criteria);
        return quoteRepository.count(specification);
    }

    /**
     * Function to convert QuoteCriteria to a {@link Specification}
     */
    private Specification<Quote> createSpecification(QuoteCriteria criteria) {
        Specification<Quote> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Quote_.id));
            }
            if (criteria.getSymbol() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSymbol(), Quote_.symbol));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Quote_.price));
            }
            if (criteria.getLastTrade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastTrade(), Quote_.lastTrade));
            }
        }
        return specification;
    }
}
