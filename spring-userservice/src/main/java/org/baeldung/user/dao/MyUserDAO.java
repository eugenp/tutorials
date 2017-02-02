package org.baeldung.user.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.baeldung.persistence.model.MyUser;
import org.springframework.stereotype.Repository;

@Repository
public class MyUserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public MyUser findByUsername(final String username) {
        final Query query = entityManager.createQuery("from MyUser where username=:username", MyUser.class);
        query.setParameter("username", username);
        final List<MyUser> result = query.getResultList();
        if (result != null && result.size() > 0) {
            return result.get(0);
        } else
            return null;
    }

    public MyUser save(final MyUser user) {
        entityManager.persist(user);
        return user;
    }

    public void removeUserByUsername(String username) {
        final Query query = entityManager.createQuery("delete from MyUser where username=:username");
        query.setParameter("username", username);
        query.executeUpdate();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
