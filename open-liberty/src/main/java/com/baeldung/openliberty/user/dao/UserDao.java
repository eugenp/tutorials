package com.baeldung.openliberty.user.dao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.baeldung.openliberty.user.model.User;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class UserDao {

    @PersistenceContext(name = "jpa-unit")
    private EntityManager em;

    public void createUser(User user) {
        em.persist(user);
    }

    public User readUser(int userId) {
        return em.find(User.class, userId);
    }

    public void updateUser(User user) {
        em.merge(user);
    }

    public void deleteUser(User user) {
        em.remove(user);
    }

    public List<User> readAllUsers() {
        return em.createNamedQuery("User.findAll", User.class).getResultList();
    }

    public List<User> findUser(String name, String location, String time) {
        return em.createNamedQuery("User.findUser", User.class)
            .setParameter("name", name)
            .setParameter("location", location)
            .setParameter("time", time).getResultList();
    }
}