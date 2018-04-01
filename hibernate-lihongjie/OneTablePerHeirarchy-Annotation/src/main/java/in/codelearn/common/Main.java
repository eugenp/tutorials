package in.codelearn.common;

import in.codelearn.model.Employee;
import in.codelearn.model.Person;
import in.codelearn.persistence.HibernateUtil;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Main {

	public static void main(String[] args) {

		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();

		
		Person person = new Person("Steve", "Balmer");
		session.save(person);

		Employee employee = new Employee("James", "Gosling", "Marketing", new Date());
		session.save(employee);
		
		session.getTransaction().commit();
		session.close();

	}
}
