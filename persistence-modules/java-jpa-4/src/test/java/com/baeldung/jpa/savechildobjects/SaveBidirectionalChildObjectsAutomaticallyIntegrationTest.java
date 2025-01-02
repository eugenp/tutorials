package com.baeldung.jpa.savechildobjects;

import static jakarta.persistence.Persistence.createEntityManagerFactory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SaveBidirectionalChildObjectsAutomaticallyIntegrationTest {

    private static EntityManager entityManager;

    @BeforeAll
    static void setup() {
        EntityManagerFactory factory = createEntityManagerFactory("jpa-savechildobjects");
        entityManager = factory.createEntityManager();
    }

    @Test
    void givenParentWithMissedAnnotation_whenCreateEntityManagerFactory_thenPersistenceExceptionExceptionThrown() {
        PersistenceException exception = assertThrows(PersistenceException.class,
          () -> createEntityManagerFactory("jpa-savechildobjects-parent-without-relationship"));
        assertThat(exception)
            .hasMessage("Basic collection has element type 'com.baeldung.jpa.savechildobjects.BidirectionalChild' which is not a known basic type (attribute is not annotated '@ElementCollection', '@OneToMany', or '@ManyToMany')");
    }

    @Test
    void givenParentWithUnidirectionalRelationship_whenSaveParentWithChildren_thenNoChildrenPresentInDB() {
        Parent parent = new Parent();

        List<UnidirectionalChild> joinColumnUnidirectionalChildren = new ArrayList<>();
        joinColumnUnidirectionalChildren.add(new UnidirectionalChild());
        joinColumnUnidirectionalChildren.add(new UnidirectionalChild());
        joinColumnUnidirectionalChildren.add(new UnidirectionalChild());

        parent.setJoinColumnUnidirectionalChildren(joinColumnUnidirectionalChildren);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(parent);
        entityManager.flush();
        transaction.commit();

        entityManager.clear();

        Parent foundParent = entityManager.find(Parent.class, parent.getId());
        assertThat(foundParent.getChildren()).isEmpty();
    }

    @Test
    void givenParentWithBidirectionalRelationship_whenSaveParentWithChildren_thenNoChildrenPresentInDB() {
        Parent parent = new Parent();
        List<BidirectionalChild> bidirectionalChildren = new ArrayList<>();
        bidirectionalChildren.add(new BidirectionalChild());
        bidirectionalChildren.add(new BidirectionalChild());
        bidirectionalChildren.add(new BidirectionalChild());

        parent.setChildren(bidirectionalChildren);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(parent);
        entityManager.flush();
        transaction.commit();

        entityManager.clear();

        Parent foundParent = entityManager.find(Parent.class, parent.getId());
        assertThat(foundParent.getChildren()).isEmpty();
    }

    @Test
    void givenParentWithCascadeTypeAndUnidirectionalRelationship_whenSaveParentWithChildren_thenAllChildrenPresentInDB() {
        ParentWithCascadeType parent = new ParentWithCascadeType();
        List<UnidirectionalChild> joinColumnUnidirectionalChildren = new ArrayList<>();
        joinColumnUnidirectionalChildren.add(new UnidirectionalChild());
        joinColumnUnidirectionalChildren.add(new UnidirectionalChild());
        joinColumnUnidirectionalChildren.add(new UnidirectionalChild());

        parent.setJoinColumnUnidirectionalChildren(joinColumnUnidirectionalChildren);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(parent);
        entityManager.flush();
        transaction.commit();

        entityManager.clear();

        ParentWithCascadeType foundParent = entityManager
          .find(ParentWithCascadeType.class, parent.getId());
        assertThat(foundParent.getJoinColumnUnidirectionalChildren())
          .hasSize(3);
    }

    @Test
    void givenParentWithCascadeTypeAndBidirectionalRelationship_whenSaveParentWithChildren_thenNoChildrenPresentInDB() {
        ParentWithCascadeType parent = new ParentWithCascadeType();
        List<BidirectionalChildWithCascadeType> bidirectionalChildren = new ArrayList<>();

        bidirectionalChildren.add(new BidirectionalChildWithCascadeType());
        bidirectionalChildren.add(new BidirectionalChildWithCascadeType());
        bidirectionalChildren.add(new BidirectionalChildWithCascadeType());

        parent.setChildren(bidirectionalChildren);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(parent);
        entityManager.flush();
        transaction.commit();

        entityManager.clear();

        ParentWithCascadeType foundParent = entityManager
          .find(ParentWithCascadeType.class, parent.getId());
        assertThat(foundParent.getChildren()).isEmpty();
    }

    @Test
    void givenParentWithCascadeType_whenSaveParentWithChildrenWithReferenceToParent_thenAllChildrenPresentInDB() {
        ParentWithCascadeType parent = new ParentWithCascadeType();
        List<BidirectionalChildWithCascadeType> bidirectionalChildren = new ArrayList<>();

        bidirectionalChildren.add(new BidirectionalChildWithCascadeType());
        bidirectionalChildren.add(new BidirectionalChildWithCascadeType());
        bidirectionalChildren.add(new BidirectionalChildWithCascadeType());

        parent.addChildren(bidirectionalChildren);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(parent);
        entityManager.flush();
        transaction.commit();

        entityManager.clear();

        ParentWithCascadeType foundParent = entityManager
          .find(ParentWithCascadeType.class, parent.getId());
        assertThat(foundParent.getChildren()).hasSize(3);
    }
}
