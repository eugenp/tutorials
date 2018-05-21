package com.baeldung.pattern.daopattern.test;

import com.baeldung.daopattern.daos.JpaUserDao;
import com.baeldung.daopattern.entities.User;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class JpaUserDaoUnitTest {
    
    private static JpaUserDao jpaUserDao;
    private static EntityManager entityManagerMock;
    
    
    @BeforeClass
    public static void setUpJpaUserDaoInstance() {
        entityManagerMock = mock(EntityManager.class);
        jpaUserDao = new JpaUserDao(entityManagerMock);
    }
    
    @Test
    public void givenJpaUserDaoInstance_whenCalledGet_thenOneAssertion() {
         assertThat(jpaUserDao.get(1)).isInstanceOf(Optional.class);
    }
    
    @Test
    public void givenEntityManagerMockInstance_whenCalledcreateQuery_thenOneAssertion() {
        Query query = entityManagerMock.createQuery("SELECT e FROM User e");
        assertThat(query).isNull();
    }
    
    @Test
    public void givenEntityManagerMockInstance_whenCalledPersist_thenCorrectVerification() {
        User user = new User();
        entityManagerMock.persist(user);
        verify(entityManagerMock).persist(user);
    }
    
    @Test
    public void givenEntityManagerMockInstance_whenCalledMerge_thenCorrectVerification() {
        User user = new User();
        entityManagerMock.merge(user);
        verify(entityManagerMock).merge(user);
    }
    
    @Test
    public void givenEntityManagerMockInstance_whenCalledRemove_thenCorrectVerification() {
        User user = new User();
        entityManagerMock.remove(user);
        verify(entityManagerMock).remove(user);
        
    }
}
