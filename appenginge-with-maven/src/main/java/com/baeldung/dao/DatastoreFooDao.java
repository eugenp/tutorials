package com.baeldung.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.baeldung.model.Foo;

public class DatastoreFooDao implements FooDao {

    public List<Foo> listFoos() {
        EntityManager em = EMFService.get().createEntityManager();
        Query q = em.createQuery("select m from Foo m");
        @SuppressWarnings("unchecked")
        List<Foo> foos = q.getResultList();
        return foos;
    }

    public void insertFoos(String user, String name, String description) {
        synchronized (this) {
            EntityManager em = EMFService.get().createEntityManager();
            Foo foo = new Foo(user, name, description);
            em.persist(foo);
            em.close();
        }
    }

    public List<Foo> getFoos(String userId) {
        EntityManager em = EMFService.get().createEntityManager();
        Query q = em.createQuery("select f from Foo f where f.user = :userId");
        q.setParameter("userId", userId);
        @SuppressWarnings("unchecked")
        List<Foo> foos = q.getResultList();
        return foos;
    }

    public void remove(long id) {
        EntityManager em = EMFService.get().createEntityManager();
        try {
            Foo foo = em.find(Foo.class, id);
            em.remove(foo);
        } finally {
            em.close();
        }
    }

}
