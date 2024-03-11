package com.baeldung.querydslvsjpacriteria;

import static com.baeldung.querydslvsjpacriteria.entities.GroupUser_.tasks;
import static com.baeldung.querydslvsjpacriteria.entities.QGroupUser.groupUser;
import static com.baeldung.querydslvsjpacriteria.entities.QTask.task;
import static com.baeldung.querydslvsjpacriteria.entities.QUserGroup.userGroup;
import static com.baeldung.querydslvsjpacriteria.entities.UserGroup_.GROUP_USERS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.querydslvsjpacriteria.entities.GroupUser;
import com.baeldung.querydslvsjpacriteria.entities.Task;
import com.baeldung.querydslvsjpacriteria.entities.UserGroup;
import com.baeldung.querydslvsjpacriteria.entities.UserGroup_;
import com.baeldung.querydslvsjpacriteria.repositories.UserGroupJpaSpecificationRepository;
import com.baeldung.querydslvsjpacriteria.repositories.UserGroupQuerydslPredicateRepository;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

@EnableJpaRepositories(basePackages = {"com.baeldung.querydslvsjpacriteria.repositories"})
@ContextConfiguration("/test-context.xml")
@ExtendWith({SpringExtension.class, TimingExtension.class})
class QuerydslVSJPACriteriaIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuerydslVSJPACriteriaIntegrationTest.class);

    private static EntityManagerFactory emf;

    private EntityManager em;

    private JPAQueryFactory queryFactory;

    @Autowired
    private UserGroupJpaSpecificationRepository userGroupJpaSpecificationRepository;

    @Autowired
    private UserGroupQuerydslPredicateRepository userQuerydslPredicateRepository;

    @BeforeAll
    static void populateDatabase() {
        emf = Persistence.createEntityManagerFactory("com.baeldung.querydsl.intro");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Stream.of("Group 1", "Group 2", "Group 3")
          .forEach(g -> {
            UserGroup userGroup = new UserGroup();
            userGroup.setName(g);
            em.persist(userGroup);
            IntStream.range(0, 10)
              .forEach(u -> {
                  GroupUser groupUser = new GroupUser();
                  groupUser.setLogin("User" + u);
                  groupUser.getUserGroups().add(userGroup);
                  em.persist(groupUser);
                  userGroup.getGroupUsers().add(groupUser);
                  IntStream.range(0, 10000)
                    .forEach(t -> {
                        Task task = new Task();
                        task.setDescription(groupUser.getLogin() + " task #" + t);
                        task.setUser(groupUser);
                        em.persist(task);
                    });
              });
              em.merge(userGroup);
          });

        em.getTransaction().commit();
        em.close();
    }

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        queryFactory = new JPAQueryFactory(em);

        createUserGroup("Group 1");
        createUserGroup("Group 4");
    }

    @Test
    void givenJpaCriteria_whenGetAllTheUserGroups_thenExpectedNumberOfItemsShouldBePresent() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserGroup> cr = cb.createQuery(UserGroup.class);
        Root<UserGroup> root = cr.from(UserGroup.class);
        CriteriaQuery<UserGroup> select = cr.select(root);

        TypedQuery<UserGroup> query = em.createQuery(select);
        List<UserGroup> results = query.getResultList();
        assertEquals(3, results.size());
    }

    @Test
    void givenQueryDSL_whenGetAllTheUserGroups_thenExpectedNumberOfItemsShouldBePresent() {
        List<UserGroup> results = queryFactory.selectFrom(userGroup).fetch();
        assertEquals(3, results.size());
    }

    @Test
    void givenJpaCriteria_whenGetTheUserGroups_thenExpectedAggregatedDataShouldBePresent() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cr = cb.createQuery(Object[].class);
        Root<UserGroup> root = cr.from(UserGroup.class);

        CriteriaQuery<Object[]> select = cr
          .multiselect(root.get(UserGroup_.name), cb.countDistinct(root.get(UserGroup_.id)))
          .where(cb.or(
              root.get(UserGroup_.name).in("Group 1", "Group 2"),
              root.get(UserGroup_.name).in("Group 4", "Group 5")
          ))
          .orderBy(cb.desc(root.get(UserGroup_.name)))
          .groupBy(root.get(UserGroup_.name));

        TypedQuery<Object[]> query = em.createQuery(select);
        List<Object[]> results = query.getResultList();
        assertEquals(2, results.size());
        assertEquals("Group 2", results.get(0)[0]);
        assertEquals(1L, results.get(0)[1]);
        assertEquals("Group 1", results.get(1)[0]);
        assertEquals(1L, results.get(1)[1]);
    }

    @Test
    void givenQueryDSL_whenGetTheUserGroups_thenExpectedAggregatedDataShouldBePresent() {
        List<Tuple> results = queryFactory
          .select(userGroup.name, userGroup.id.countDistinct())
          .from(userGroup)
          .where(userGroup.name.in("Group 1", "Group 2")
            .or(userGroup.name.in("Group 4", "Group 5")))
          .orderBy(userGroup.name.desc())
          .groupBy(userGroup.name)
          .fetch();

        assertEquals(2, results.size());
        assertEquals("Group 2", results.get(0).get(userGroup.name));
        assertEquals(1L, results.get(0).get(userGroup.id.countDistinct()));
        assertEquals("Group 1", results.get(1).get(userGroup.name));
        assertEquals(1L, results.get(1).get(userGroup.id.countDistinct()));
    }

    @Test
    void givenJpaCriteria_whenGetTheUserGroupsWithJoins_thenExpectedDataShouldBePresent() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserGroup> query = cb.createQuery(UserGroup.class);

        query.from(UserGroup.class)
          .<UserGroup, GroupUser>join(GROUP_USERS, JoinType.LEFT)
          .join(tasks, JoinType.LEFT);

        List<UserGroup> result = em.createQuery(query).getResultList();
        assertUserGroups(result);
    }

    private void assertUserGroups(List<UserGroup> userGroups) {
        assertEquals(3, userGroups.size());
        for (UserGroup group : userGroups) {
            assertEquals(10, group.getGroupUsers().size());
            for (GroupUser user : group.getGroupUsers()) {
                assertEquals(10000, user.getTasks().size());
            }
        }
    }

    @Test
    void givenQueryDSL_whenGetTheUserGroupsWithJoins_thenExpectedDataShouldBePresent() {
        List<UserGroup> result = queryFactory
          .selectFrom(userGroup)
          .leftJoin(userGroup.groupUsers, groupUser)
          .leftJoin(groupUser.tasks, task)
          .fetch();

        assertUserGroups(result);
    }

    @Test
    void givenJpaCriteria_whenModifyTheUserGroup_thenNameShouldBeUpdated() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<UserGroup> criteriaUpdate = cb.createCriteriaUpdate(UserGroup.class);
        Root<UserGroup> root = criteriaUpdate.from(UserGroup.class);
        criteriaUpdate.set(UserGroup_.name, "Group 1 Updated using Jpa Criteria");
        criteriaUpdate.where(cb.equal(root.get(UserGroup_.name), "Group 1"));

        em.createQuery(criteriaUpdate).executeUpdate();
        UserGroup foundGroup = em.find(UserGroup.class, 1L);
        assertEquals("Group 1 Updated using Jpa Criteria", foundGroup.getName());
        renameEntityBack(foundGroup, "Group 1");
    }

    private void renameEntityBack(UserGroup foundGroup, String name) {
        foundGroup.setName(name);
        em.merge(foundGroup);
    }

    @Test
    void givenQueryDSL_whenModifyTheUserGroup_thenNameShouldBeUpdated() {
        queryFactory.update(userGroup)
          .set(userGroup.name, "Group 1 Updated Using QueryDSL")
          .where(userGroup.name.eq("Group 1"))
          .execute();

        UserGroup foundGroup = em.find(UserGroup.class, 1L);
        assertEquals("Group 1 Updated Using QueryDSL", foundGroup.getName());
        renameEntityBack(foundGroup, "Group 1");
    }

    @Test
    void givenJpaSpecificationRepository_whenGetTheUserGroups_thenExpectedDataShouldBePresent() {
        List<UserGroup> results = userGroupJpaSpecificationRepository.findAllWithNameInAnyList(
          List.of("Group 1", "Group 2"), List.of("Group 4", "Group 5"));

        assertEquals(2, results.size());
        assertEquals("Group 1", results.get(0).getName());
        assertEquals("Group 4", results.get(1).getName());
    }

    @Test
    void givenQuerydslPredicateRepository_whenGetTheUserGroups_thenExpectedDataShouldBePresent() {
        List<UserGroup> results = userQuerydslPredicateRepository.findAllWithNameInAnyList(
          List.of("Group 1", "Group 2"), List.of("Group 4", "Group 5"));

        assertEquals(2, results.size());
        assertEquals("Group 1", results.get(0).getName());
        assertEquals("Group 4", results.get(1).getName());
    }

    private void createUserGroup(String name) {
        UserGroup entity = new UserGroup();
        entity.setName(name);
        userGroupJpaSpecificationRepository.save(entity);
    }

    @AfterEach
    void tearDown() {
        em.getTransaction().commit();
        em.close();
        userGroupJpaSpecificationRepository.deleteAll();
    }

    @AfterAll
    static void afterClass() {
        emf.close();
    }
}
