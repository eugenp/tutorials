package com.baeldung.hibernatesearch;

import com.baeldung.hibernatesearch.model.Product;
import org.apache.lucene.search.Query;
import org.hibernate.search.engine.ProjectionConstants;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProductSearchDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Product> searchProductNameByKeywordQuery(String text) {

        Query keywordQuery = getQueryBuilder()
            .keyword()
            .onField("productName")
            .matching(text)
            .createQuery();

        List<Product> results = getJpaQuery(keywordQuery).getResultList();

        return results;
    }

    public List<Product> searchProductNameByFuzzyQuery(String text) {

        Query fuzzyQuery = getQueryBuilder()
            .keyword()
            .fuzzy()
            .withEditDistanceUpTo(2)
            .withPrefixLength(0)
            .onField("productName")
            .matching(text)
            .createQuery();

        List<Product> results = getJpaQuery(fuzzyQuery).getResultList();

        return results;
    }

    public List<Product> searchProductNameByWildcardQuery(String text) {

        Query wildcardQuery = getQueryBuilder()
            .keyword()
            .wildcard()
            .onField("productName")
            .matching(text)
            .createQuery();

        List<Product> results = getJpaQuery(wildcardQuery).getResultList();

        return results;
    }

    public List<Product> searchProductDescriptionByPhraseQuery(String text) {

        Query phraseQuery = getQueryBuilder()
            .phrase()
            .withSlop(1)
            .onField("description")
            .sentence(text)
            .createQuery();

        List<Product> results = getJpaQuery(phraseQuery).getResultList();

        return results;
    }

    public List<Product> searchProductNameAndDescriptionBySimpleQueryStringQuery(String text) {

        Query simpleQueryStringQuery = getQueryBuilder()
            .simpleQueryString()
            .onFields("productName", "description")
            .matching(text)
            .createQuery();

        List<Product> results = getJpaQuery(simpleQueryStringQuery).getResultList();

        return results;
    }

    public List<Product> searchProductNameByRangeQuery(int low, int high) {

        Query rangeQuery = getQueryBuilder()
            .range()
            .onField("memory")
            .from(low)
            .to(high)
            .createQuery();

        List<Product> results = getJpaQuery(rangeQuery).getResultList();

        return results;
    }

    public List<Object[]> searchProductNameByMoreLikeThisQuery(Product entity) {

        Query moreLikeThisQuery = getQueryBuilder()
            .moreLikeThis()
            .comparingField("productName")
            .toEntity(entity)
            .createQuery();

        List<Object[]> results = getJpaQuery(moreLikeThisQuery).setProjection(ProjectionConstants.THIS, ProjectionConstants.SCORE)
            .getResultList();

        return results;
    }

    public List<Product> searchProductNameAndDescriptionByKeywordQuery(String text) {

        Query keywordQuery = getQueryBuilder()
            .keyword()
            .onFields("productName", "description")
            .matching(text)
            .createQuery();

        List<Product> results = getJpaQuery(keywordQuery).getResultList();

        return results;
    }

    public List<Object[]> searchProductNameAndDescriptionByMoreLikeThisQuery(Product entity) {

        Query moreLikeThisQuery = getQueryBuilder()
            .moreLikeThis()
            .comparingField("productName")
            .toEntity(entity)
            .createQuery();

        List<Object[]> results = getJpaQuery(moreLikeThisQuery).setProjection(ProjectionConstants.THIS, ProjectionConstants.SCORE)
            .getResultList();

        return results;
    }

    public List<Product> searchProductNameAndDescriptionByCombinedQuery(String manufactorer, int memoryLow, int memoryTop, String extraFeature, String exclude) {

        Query combinedQuery = getQueryBuilder()
            .bool()
            .must(getQueryBuilder().keyword()
                .onField("productName")
                .matching(manufactorer)
                .createQuery())
            .must(getQueryBuilder()
                .range()
                .onField("memory")
                .from(memoryLow)
                .to(memoryTop)
                .createQuery())
            .should(getQueryBuilder()
                .phrase()
                .onField("description")
                .sentence(extraFeature)
                .createQuery())
            .must(getQueryBuilder()
                .keyword()
                .onField("productName")
                .matching(exclude)
                .createQuery())
            .not()
            .createQuery();

        List<Product> results = getJpaQuery(combinedQuery).getResultList();

        return results;
    }

    private FullTextQuery getJpaQuery(org.apache.lucene.search.Query luceneQuery) {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        return fullTextEntityManager.createFullTextQuery(luceneQuery, Product.class);
    }

    private QueryBuilder getQueryBuilder() {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        return fullTextEntityManager.getSearchFactory()
            .buildQueryBuilder()
            .forEntity(Product.class)
            .get();
    }
}
