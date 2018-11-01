package com.baeldung.repo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.baeldung.domain.EmployeeRepository;
import com.baeldung.model.Employee;

public class DatabaseEmployeeRepository implements EmployeeRepository {

	private static final String PERSISTENCE_UNIT_NAME = "Employee";
	private static EntityManagerFactory factory;

	@Override
	public Employee saveEmployee(Employee emp) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		em.persist(emp);
		em.getTransaction().commit();

		em.close();
		return emp;
	}

	public Employee getEmployeeById(Integer Id) {
		EntityManager em = factory.createEntityManager();
		Employee emp = em.find(Employee.class, Id);
		em.close();
		return emp;
	}

	private DatabaseEmployeeRepository() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	public static DatabaseEmployeeRepository getInstance() {
		return DatabaseEmployeeRepositorySubClass.INSTANCE;
	}

	private static class DatabaseEmployeeRepositorySubClass {

		private static DatabaseEmployeeRepository INSTANCE = new DatabaseEmployeeRepository();
	}
}
