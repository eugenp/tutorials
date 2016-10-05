package org.baeldung.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.baeldung.persistence.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class MyUserRepository implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> findAll(Sort sort) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> findAll(Iterable<Long> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends User> List<S> save(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void flush() {
        // TODO Auto-generated method stub

    }

    @Override
    public <S extends User> S saveAndFlush(S entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<User> entities) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteAllInBatch() {
        // TODO Auto-generated method stub

    }

    @Override
    public User getOne(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<User> findAll(Pageable arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long count() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void delete(Long arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(User arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(Iterable<? extends User> arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean exists(Long arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public User findOne(Long arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends User> S save(S user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public User findByUsername(String username) {
        Query query = entityManager.createQuery("from User where username=:username", User.class);
        query.setParameter("username", username);
        List<User> result = query.getResultList();
        if (result != null && result.size() > 0) {
            return result.get(0);
        } else
            return null;
    }
    
    public void removeUserByUsername(String username) {
        final Query query = entityManager.createQuery("delete from User where username=:username");
        query.setParameter("username", username);
        query.executeUpdate();
    }

}
