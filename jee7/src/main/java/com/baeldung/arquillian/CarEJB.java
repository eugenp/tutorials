package com.baeldung.arquillian;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class CarEJB {
    @PersistenceContext(unitName = "defaultPersistenceUnit")
    private EntityManager em;

    public Car saveCar(final Car car) {
        em.persist(car);
        return car;
    }

    @SuppressWarnings("unchecked")
    public List<Car> findAllCars() {
        final Query query = em.createQuery("SELECT b FROM Car b ORDER BY b.name ASC");
        List<Car> entries = query.getResultList();
        if (entries == null) {
            entries = new ArrayList<Car>();
        }
        return entries;
    }

    public void deleteCar(Car car) {
        car = em.merge(car);
        em.remove(car);
    }
}