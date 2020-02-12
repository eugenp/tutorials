package com.baeldung.joins;

import static org.assertj.core.api.Assertions.assertThat;

import com.baeldung.joins.model.Department;
import com.baeldung.joins.model.Phone;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("joins")
public class JpaJoinsIntegrationTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void whenPathExpressionIsUsedForSingleValuedAssociation_thenCreatesImplicitInnerJoin() {
        TypedQuery<Department> query = entityManager.createQuery("SELECT e.department FROM Employee e", Department.class);

        List<Department> resultList = query.getResultList();

        assertThat(resultList).hasSize(3);
        assertThat(resultList).extracting("name")
            .containsOnly("Infra", "Accounting", "Accounting");
    }

    @Test
    public void whenJoinKeywordIsUsed_thenCreatesExplicitInnerJoin() {
        TypedQuery<Department> query = entityManager.createQuery("SELECT d FROM Employee e JOIN e.department d", Department.class);

        List<Department> resultList = query.getResultList();

        assertThat(resultList).hasSize(3);
        assertThat(resultList).extracting("name")
            .containsOnly("Infra", "Accounting", "Accounting");
    }

    @Test
    public void whenInnerJoinKeywordIsUsed_thenCreatesExplicitInnerJoin() {
        TypedQuery<Department> query = entityManager.createQuery("SELECT d FROM Employee e INNER JOIN e.department d", Department.class);

        List<Department> resultList = query.getResultList();

        assertThat(resultList).hasSize(3);
        assertThat(resultList).extracting("name")
            .containsOnly("Infra", "Accounting", "Accounting");
    }

    @Test
    public void whenEntitiesAreListedInFromAndMatchedInWhere_ThenCreatesJoin() {
        TypedQuery<Department> query = entityManager.createQuery("SELECT d FROM Employee e, Department d WHERE e.department = d", Department.class);

        List<Department> resultList = query.getResultList();

        assertThat(resultList).hasSize(3);
        assertThat(resultList).extracting("name")
            .containsOnly("Infra", "Accounting", "Accounting");
    }

    @Test
    public void whenEntitiesAreListedInFrom_ThenCreatesCartesianProduct() {
        TypedQuery<Department> query = entityManager.createQuery("SELECT d FROM Employee e, Department d", Department.class);

        List<Department> resultList = query.getResultList();

        assertThat(resultList).hasSize(9);
        assertThat(resultList).extracting("name")
            .containsOnly("Infra", "Accounting", "Management", "Infra", "Accounting", "Management", "Infra", "Accounting", "Management");
    }

    @Test
    public void whenCollectionValuedAssociationIsJoined_ThenCanSelect() {
        TypedQuery<Phone> query = entityManager.createQuery("SELECT ph FROM Employee e JOIN e.phones ph WHERE ph LIKE '1%'", Phone.class);

        List<Phone> resultList = query.getResultList();

        assertThat(resultList).hasSize(1);
    }

    @Test
    public void whenMultipleEntitiesAreListedWithJoin_ThenCreatesMultipleJoins() {
        TypedQuery<Phone> query = entityManager.createQuery("SELECT ph FROM Employee e JOIN e.department d JOIN e.phones ph WHERE d.name IS NOT NULL", Phone.class);

        List<Phone> resultList = query.getResultList();

        assertThat(resultList).hasSize(3);
        assertThat(resultList).extracting("number")
            .containsOnly("111", "222", "333");
    }

    @Test
    public void whenLeftKeywordIsSpecified_thenCreatesOuterJoinAndIncludesNonMatched() {
        TypedQuery<Department> query = entityManager.createQuery("SELECT DISTINCT d FROM Department d LEFT JOIN d.employees e", Department.class);

        List<Department> resultList = query.getResultList();

        assertThat(resultList).hasSize(3);
        assertThat(resultList).extracting("name")
            .containsOnly("Infra", "Accounting", "Management");
    }

    @Test
    public void whenFetchKeywordIsSpecified_ThenCreatesFetchJoin() {
        TypedQuery<Department> query = entityManager.createQuery("SELECT d FROM Department d JOIN FETCH d.employees", Department.class);

        List<Department> resultList = query.getResultList();

        assertThat(resultList).hasSize(3);
        assertThat(resultList).extracting("name")
            .containsOnly("Infra", "Accounting", "Accounting");
    }

    @Test
    public void whenLeftAndFetchKeywordsAreSpecified_ThenCreatesOuterFetchJoin() {
        TypedQuery<Department> query = entityManager.createQuery("SELECT d FROM Department d LEFT JOIN FETCH d.employees", Department.class);

        List<Department> resultList = query.getResultList();

        assertThat(resultList).hasSize(4);
        assertThat(resultList).extracting("name")
            .containsOnly("Infra", "Accounting", "Accounting", "Management");
    }

    @Test
    public void whenCollectionValuedAssociationIsSpecifiedInSelect_ThenReturnsCollections() {
        TypedQuery<Collection> query = entityManager.createQuery("SELECT e.phones FROM Employee e", Collection.class);

        List<Collection> resultList = query.getResultList();

        assertThat(resultList).extracting("number").containsOnly("111", "222", "333");
    }
}
