package com.baeldung.daopattern.daos;

import com.baeldung.daopattern.entities.User;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class JpaUserDao implements Dao<User> {
    
    private final EntityManager entityManager;
    
    public JpaUserDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public Optional<User> get(long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }
    
    @Override
    public List<User> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM User e");
        return query.getResultList();
    }
    
    @Override
    public void save(User user) {
        executeInsideTransaction(entityManager -> entityManager.persist(user));
    }
    
    @Override
    public void update(User user, String[] params) {
        user.setName(Objects.requireNonNull(params[0], "Name cannot be null"));
        user.setEmail(Objects.requireNonNull(params[1], "Email cannot be null"));
        executeInsideTransaction(entityManager -> entityManager.merge(user));
    }
    
    @Override 
    public void delete(User user) {
        executeInsideTransaction(entityManager -> entityManager.remove(user));
    }
    
    private void executeInsideTransaction(Consumer<EntityManager> action) {
        final EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit(); 
        }
        catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }    
}
