package com.baeldung.jpa.clonenetity;

import static jakarta.persistence.Persistence.createEntityManagerFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.jpa.cloneentity.Category;
import com.baeldung.jpa.cloneentity.Product;
import com.baeldung.jpa.cloneentity.ProductService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class CloneEntityIntegrationTest {

    private static final EntityManagerFactory factory = createEntityManagerFactory("jpa-cloneentity");
    private EntityManager em;

    @BeforeEach
    void createEntityManager() {
        em = factory.createEntityManager();
    }

    @Test
    public void whenManualClone_thenReturnsNewEntityWithReferenceToRelatedEntities() {
        // Create and set up the original Product
        Product original = new Product();
        original.setId(1L);
        original.setName("Smartphone");
        original.setCategory(new Category(1L, "Electronics"));
        original.setPrice(499.99);

        ProductService service = new ProductService();
        Product clone = service.manualClone(original);

        assertNull(clone.getId());
        assertEquals(original.getName(), clone.getName());
        assertEquals(original.getCategory().getName(), clone.getCategory().getName());
        assertNotSame(original, clone, "Cloned product should be a different instance");
        assertSame(original.getCategory(), clone.getCategory(), "Category should be the same instance (shallow copy)");
    }

    @Test
    public void whenManualCloneDeep_thenReturnsNewEntityWithNewRelatedEntities() {
        // Create and set up the original Product
        Product original = new Product();
        original.setId(1L);
        original.setName("Smartphone");
        original.setCategory(new Category(1L, "Electronics"));
        original.setPrice(499.99);

        ProductService service = new ProductService();
        Product clone = service.manualDeepClone(original);

        assertNull(clone.getId());
        assertEquals(original.getName(), clone.getName());
        assertEquals(original.getCategory().getName(), clone.getCategory().getName());
        assertNotSame(original, clone, "Cloned product should be a different instance");
        assertNotSame(original.getCategory(), clone.getCategory(), "Category should be the same instance (shallow copy)");
    }

    @Test
    void whenUsingSerializationClone_thenReturnsNewEntityWithNewNestedEntities() throws IOException, ClassNotFoundException {
        Product original = new Product();
        original.setId(1L);
        original.setName("Laptop");
        original.setCategory(new Category(1L, "Electronics"));
        original.setPrice(999.99);

        ProductService service = new ProductService();
        Product clone = service.cloneUsingSerialization(original);

        assertNull(clone.getId());

        assertEquals(original.getName(), clone.getName());
        assertEquals(original.getCategory()
            .getName(), clone.getCategory()
            .getName());

        assertNotSame(original, clone, "Cloned product should be a different instance");
        assertNotSame(original.getCategory(), clone.getCategory(), "Cloned category should be a different instance");
    }


    @Test
    public void whenUsingBeanUtilsClone_thenReturnsNewEntityWithNullNestedEntities() throws InvocationTargetException, IllegalAccessException {
        Product original = new Product();
        original.setId(1L);
        original.setName("Laptop");
        original.setCategory(new Category(1L, "Electronics"));
        original.setPrice(999.99);

        ProductService service = new ProductService();
        Product clone = service.cloneUsingBeanUtils(original);

        assertNull(clone.getId());

        assertEquals(original.getName(), clone.getName());

        assertNotSame(original, clone, "Cloned product should be a different instance");
        assertNull(clone.getCategory(), "Category will not be copied");
    }


    @Test
    public void whenUsingModelMapperClone_thenReturnsNewEntityWithNewNestedEntities() {
        Product original = new Product();
        original.setId(1L);
        original.setName("Laptop");
        original.setCategory(new Category(1L, "Electronics"));
        original.setPrice(999.99);

        ProductService service = new ProductService();
        Product clone = service.cloneUsingModelMapper(original);

        assertNull(clone.getId());

        assertEquals(original.getName(), clone.getName(), "Names should match");

        assertNotSame(original, clone, "Cloned product should be a different instance");
        assertNotSame(original.getCategory(), clone.getCategory(), "Category should be a different instance (deep copy)");
    }


    @Test
    public void whenUsingClonenable_thenReturnsNewEntityWithReferenceToRelatedEntities() throws CloneNotSupportedException {
        // Create and set up the original Product
        Product original = new Product();
        original.setId(1L);
        original.setName("Smartphone");
        original.setCategory(new Category(1L, "Electronics"));
        original.setPrice(599.99);

        Product clone = original.clone();

        assertNull(clone.getId(), "Cloned product ID should be null");

        assertEquals(original.getName(), clone.getName(), "Names should match");
        assertEquals(original.getCategory().getName(), clone.getCategory().getName(), "Category names should match");
        assertEquals(original.getPrice(), clone.getPrice(), "Prices should match");

        assertNotSame(original, clone, "Cloned product should be a different instance");

        assertSame(original.getCategory(), clone.getCategory(), "Category should be the same instance (shallow copy)");
    }


    @Test
    void whenUsingDetach_thenReturnsNewEntity() {
        em.getTransaction().begin();
        Product original = new Product();
        original.setId(1L);
        original.setName("Smartphone");
        original.setCategory(new Category(1L, "Electronics"));
        original.setPrice(599.99);
        em.merge(original);
        em.getTransaction().commit();

        original = em.find(Product.class, 1L);
        em.detach(original);

        em.getTransaction().begin();
        original.setId(2L);
        original.setName("Laptop");
        Product clone = em.merge(original);
        original = em.find(Product.class, 1L);

        assertSame("Laptop", clone.getName());
        assertSame("Smartphone", original.getName());
    }

    @Test
    void whenWithoutDetach_thenOriginalEntityModified() {
        em.getTransaction().begin();
        Product original = new Product();
        original.setId(1L);
        original.setName("Smartphone");
        original.setCategory(new Category(1L, "Electronics"));
        original.setPrice(599.99);
        em.merge(original);
        em.getTransaction().commit();

        original = em.find(Product.class, 1L);
        //em.detach(original);

        em.getTransaction().begin();
        original.setId(2L);
        original.setName("Laptop");
        Product clone = em.merge(original);
        original = em.find(Product.class, 1L);

        assertSame("Laptop", clone.getName());
        assertSame("Laptop", original.getName());
    }
}
