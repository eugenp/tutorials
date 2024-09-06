package com.baeldung.jpa.castvstreat;

import static jakarta.persistence.Persistence.createEntityManagerFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.h2.jdbc.JdbcSQLDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

public class CastVsTreatLiveTest {

    private static final EntityManagerFactory factory = createEntityManagerFactory("jpa-castvstreat");
    private EntityManager em;

    @BeforeEach
    void createEntityManager() {
        em = factory.createEntityManager();
    }

    @Test
    void givenStringSalary_whenCastToInteger_thenCorrectResult() {
        em.getTransaction().begin();

        Employee emp1 = new Employee();
        emp1.setId(1L);
        emp1.setSalary("5000");

        Employee emp2 = new Employee();
        emp2.setId(2L);
        emp2.setSalary("7000");

        em.merge(emp1);
        em.merge(emp2);

        em.getTransaction().commit();

        String queryStr = "SELECT CAST(e.salary AS Integer) FROM Employee e";

        Query query = em.createQuery(queryStr);
        List<Integer> salaries = query.getResultList();

        assertEquals(2, salaries.size());
        assertEquals(5000, salaries.get(0));
        assertEquals(7000, salaries.get(1));
    }

    @Test
    void givenVehicle_whenTreatAsCar_thenCorrectResult() {
        em.getTransaction().begin();

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setType("Bike");

        Car car = new Car();
        car.setId(2L);
        car.setType("Car");
        car.setNumberOfDoors(4);

        em.merge(vehicle);
        em.merge(car);

        em.getTransaction().commit();

        String queryStr = "SELECT TREAT(v AS Car) FROM Vehicle v WHERE v.type = 'Car'";

        Query query = em.createQuery(queryStr);
        List<Car> cars = query.getResultList();

        assertEquals(1, cars.size());
        assertEquals(4, cars.get(0).getNumberOfDoors());
    }

    @Test
    void givenStringSalaryWithAlphabet_whenCastToInteger_thenThrowException() {
        em.getTransaction().begin();

        Employee emp1 = new Employee();
        emp1.setId(1L);
        emp1.setSalary("5ooo");

        em.merge(emp1);

        em.getTransaction().commit();

        String queryStr = "SELECT CAST(e.salary AS Integer) FROM Employee e";

        try {
            Query query = em.createQuery(queryStr);
            query.getResultList(); // This should throw an exception
            fail("Expected a JdbcSQLDataException to be thrown");
        } catch (PersistenceException e) {
            assertTrue(e.getCause() instanceof JdbcSQLDataException, "Expected a JdbcSQLDataException to be thrown");
        }
    }

    @Test
    void givenBike_whenTreatAsCar_thenReturnEmpty() {
        em.getTransaction().begin();

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setType("Bike");

        Car car = new Car();
        car.setId(2L);
        car.setType("Car");
        car.setNumberOfDoors(4);

        em.merge(vehicle);
        em.merge(car);

        em.getTransaction().commit();

        String queryStr = "SELECT TREAT(v AS Car) FROM Vehicle v WHERE v.type = 'Bike'";

        Query query = em.createQuery(queryStr);
        List<Car> cars = query.getResultList();

        assertEquals(0, cars.size());
    }

    @Test
    void givenStringSalary_whenCastToIntegerUsingCriteriaBuilder_thenConvertedSuccessfully() {
        em.getTransaction().begin();

        // Insert test data for Employee
        Employee emp1 = new Employee();
        emp1.setId(1L);
        emp1.setSalary("5000");
        em.merge(emp1);

        em.getTransaction().commit();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
        Root<Employee> employee = cq.from(Employee.class);

        Expression<String> salaryExpression = employee.get("salary");
        Expression<Integer> salaryAsInteger = salaryExpression.as(Integer.class);

        cq.select(salaryAsInteger);

        TypedQuery<Integer> query = em.createQuery(cq);

        List<Integer> salaries = query.getResultList();
        assertEquals(5000, salaries.get(0));
    }

    @Test
    void givenVehicleTypeCar_whenTreatAsCarUsingCriteriaBuilder_thenRetrieveCarsSuccessfully() {
        em.getTransaction().begin();

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setType("Bike");

        Car car = new Car();
        car.setId(2L);
        car.setType("Car");
        car.setNumberOfDoors(4);

        em.merge(vehicle);
        em.merge(car);

        em.getTransaction().commit();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Car> cq = cb.createQuery(Car.class);
        Root<Vehicle> vehicleRoot = cq.from(Vehicle.class);

        cq.select(cb.treat(vehicleRoot, Car.class))
            .where(cb.equal(vehicleRoot.get("type"), "Car"));

        TypedQuery<Car> query = em.createQuery(cq);
        List<Car> cars = query.getResultList();

        assertEquals(1, cars.size());
        assertEquals(4, cars.get(0).getNumberOfDoors());
    }
}
