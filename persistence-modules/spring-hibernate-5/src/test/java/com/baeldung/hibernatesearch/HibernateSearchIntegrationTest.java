package com.baeldung.hibernatesearch;

import com.baeldung.hibernatesearch.model.Product;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.*;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { HibernateSearchConfig.class }, loader = AnnotationConfigContextLoader.class)
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HibernateSearchIntegrationTest {

    @Autowired
    ProductSearchDao dao;

    @PersistenceContext
    private EntityManager entityManager;

    private List<Product> products;

    @Before
    public void setupTestData() {

        products = Arrays.asList(new Product(1, "Apple iPhone X 256 GB", 256, "The current high-end smartphone from Apple, with lots of memory and also Face ID"),
            new Product(2, "Apple iPhone X 128 GB", 128, "The current high-end smartphone from Apple, with Face ID"), new Product(3, "Apple iPhone 8 128 GB", 128, "The latest smartphone from Apple within the regular iPhone line, supporting wireless charging"),
            new Product(4, "Samsung Galaxy S7 128 GB", 64, "A great Android smartphone"), new Product(5, "Microsoft Lumia 650 32 GB", 32, "A cheaper smartphone, coming with Windows Mobile"),
            new Product(6, "Microsoft Lumia 640 32 GB", 32, "A cheaper smartphone, coming with Windows Mobile"), new Product(7, "Microsoft Lumia 630 16 GB", 16, "A cheaper smartphone, coming with Windows Mobile"));
    }

    @Commit
    @Test
    public void testA_whenInitialTestDataInserted_thenSuccess() {

        for (int i = 0; i < products.size() - 1; i++) {
            entityManager.persist(products.get(i));
        }
    }

    @Test
    public void testB_whenIndexInitialized_thenCorrectIndexSize() throws InterruptedException {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        fullTextEntityManager.createIndexer()
            .startAndWait();
        int indexSize = fullTextEntityManager.getSearchFactory()
            .getStatistics()
            .getNumberOfIndexedEntities(Product.class.getName());

        assertEquals(products.size() - 1, indexSize);
    }

    @Commit
    @Test
    public void testC_whenAdditionalTestDataInserted_thenSuccess() {

        entityManager.persist(products.get(products.size() - 1));
    }

    @Test
    public void testD_whenAdditionalTestDataInserted_thenIndexUpdatedAutomatically() {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        int indexSize = fullTextEntityManager.getSearchFactory()
            .getStatistics()
            .getNumberOfIndexedEntities(Product.class.getName());

        assertEquals(products.size(), indexSize);
    }

    @Test
    public void testE_whenKeywordSearchOnName_thenCorrectMatches() {
        List<Product> expected = Arrays.asList(products.get(0), products.get(1), products.get(2));
        List<Product> results = dao.searchProductNameByKeywordQuery("iphone");

        assertThat(results, containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void testF_whenFuzzySearch_thenCorrectMatches() {
        List<Product> expected = Arrays.asList(products.get(0), products.get(1), products.get(2));
        List<Product> results = dao.searchProductNameByFuzzyQuery("iPhaen");

        assertThat(results, containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void testG_whenWildcardSearch_thenCorrectMatches() {
        List<Product> expected = Arrays.asList(products.get(4), products.get(5), products.get(6));
        List<Product> results = dao.searchProductNameByWildcardQuery("6*");

        assertThat(results, containsInAnyOrder(expected.toArray()));

    }

    @Test
    public void testH_whenPhraseSearch_thenCorrectMatches() {
        List<Product> expected = Arrays.asList(products.get(2));
        List<Product> results = dao.searchProductDescriptionByPhraseQuery("with wireless charging");

        assertThat(results, containsInAnyOrder(expected.toArray()));

    }

    @Test
    public void testI_whenSimpleQueryStringSearch_thenCorrectMatches() {
        List<Product> expected = Arrays.asList(products.get(0), products.get(1));
        List<Product> results = dao.searchProductNameAndDescriptionBySimpleQueryStringQuery("Aple~2 + \"iPhone X\" + (256 | 128)");

        assertThat(results, containsInAnyOrder(expected.toArray()));

    }

    @Test
    public void testJ_whenRangeSearch_thenCorrectMatches() {
        List<Product> expected = Arrays.asList(products.get(0), products.get(1), products.get(2), products.get(3));
        List<Product> results = dao.searchProductNameByRangeQuery(64, 256);

        assertThat(results, containsInAnyOrder(expected.toArray()));

    }

    @Test
    public void testK_whenMoreLikeThisSearch_thenCorrectMatchesInOrder() {
        List<Product> expected = products;
        List<Object[]> resultsWithScore = dao.searchProductNameByMoreLikeThisQuery(products.get(0));
        List<Product> results = new LinkedList<Product>();

        for (Object[] resultWithScore : resultsWithScore) {
            results.add((Product) resultWithScore[0]);
        }

        assertThat(results, contains(expected.toArray()));

    }

    @Test
    public void testL_whenKeywordSearchOnNameAndDescription_thenCorrectMatches() {
        List<Product> expected = Arrays.asList(products.get(0), products.get(1), products.get(2));
        List<Product> results = dao.searchProductNameAndDescriptionByKeywordQuery("iphone");

        assertThat(results, containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void testM_whenMoreLikeThisSearchOnProductNameAndDescription_thenCorrectMatchesInOrder() {
        List<Product> expected = products;
        List<Object[]> resultsWithScore = dao.searchProductNameAndDescriptionByMoreLikeThisQuery(products.get(0));
        List<Product> results = new LinkedList<Product>();

        for (Object[] resultWithScore : resultsWithScore) {
            results.add((Product) resultWithScore[0]);
        }

        assertThat(results, contains(expected.toArray()));
    }

    @Test
    public void testN_whenCombinedSearch_thenCorrectMatches() {
        List<Product> expected = Arrays.asList(products.get(1), products.get(2));
        List<Product> results = dao.searchProductNameAndDescriptionByCombinedQuery("apple", 64, 128, "face id", "samsung");

        assertThat(results, containsInAnyOrder(expected.toArray()));
    }
}
