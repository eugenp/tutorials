
// // import org.junit.jupiter.api.Assertions;
// // import org.junit.jupiter.api.Test;
// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// // import org.springframework.boot.test.context.SpringBootTest;
// // import org.springframework.transaction.annotation.Transactional;

// // import java.util.Collections;
// // import java.util.List;

// // @DataJpaTest
// // @Transactional
// // public class UnidirectionalUnitTest {

// //     @Autowired
// //     private EntityManager entityManager;

// //     @Test
// //     public void givenBookWithAuthor_whenSaved_thenFindBookByAuthor() {
// //         // given
// //         Author author = new Author();
// //         author.setName("John Doe");

// //         Book book = new Book();
// //         book.setTitle("My Book");
// //         book.setAuthors(Collections.singleton(author));

// //         entityManager.persist(author);
// //         entityManager.persist(book);

// //         entityManager.flush();
// //         entityManager.clear();

// //         // when
// //         List<Book> booksByAuthor = entityManager.createQuery(
// //                 "select b from Book b join b.authors a where a.name = :name", Book.class)
// //                 .setParameter("name", "John Doe")
// //                 .getResultList();

// //         // then
// //         Assertions.assertEquals(1, booksByAuthor.size());
// //         Assertions.assertEquals(book, booksByAuthor.get(0));
// //     }
// // }


// package com.baeldung.hibernate.associations;

// import com.baeldung.associations.unidirectional.*;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

// import java.util.List;
// import java.util.Set;

// import static org.assertj.core.api.Assertions.assertThat;

// @DataJpaTest
// public class UnidirectionalUnitTest {

//     @Autowired
//     private TestEntityManager entityManager;

//     @Test
//     public void givenDepartmentWithEmployees_whenFindById_thenDepartmentWithEmployeesReturned() {
//         // given
//         Employee employee1 = new Employee();
//         Employee employee2 = new Employee();
//         Department department = new Department();
//         department.setEmployees(List.of(employee1, employee2));
//         entityManager.persist(department);
//         entityManager.flush();

//         // when
//         Department foundDepartment = entityManager.find(Department.class, department.getId());

//         // then
//         assertThat(foundDepartment).isEqualTo(department);
//         assertThat(foundDepartment.getEmployees()).containsExactly(employee1, employee2);
//     }

//     @Test
//     public void givenEmployeeWithParkingSpot_whenFindById_thenEmployeeWithParkingSpotReturned() {
//         // given
//         ParkingSpot parkingSpot = new ParkingSpot();
//         entityManager.persist(parkingSpot);
//         entityManager.flush();
//         Employee employee = new Employee();
//         employee.setParkingSpot(parkingSpot);
//         entityManager.persist(employee);
//         entityManager.flush();

//         // when
//         Employee foundEmployee = entityManager.find(Employee.class, employee.getId());

//         // then
//         assertThat(foundEmployee).isEqualTo(employee);
//         assertThat(foundEmployee.getParkingSpot()).isEqualTo(parkingSpot);
//     }

//     @Test
//     public void givenBookWithAuthors_whenFindById_thenBookWithAuthorsReturned() {
//         // given
//         Author author1 = new Author();
//         Author author2 = new Author();
//         entityManager.persist(author1);
//         entityManager.persist(author2);
//         entityManager.flush();
//         Book book = new Book();
//         book.setAuthors(Set.of(author1, author2));
//         entityManager.persist(book);
//         entityManager.flush();

//         // when
//         Book foundBook = entityManager.find(Book.class, book.getId());

//         // then
//         assertThat(foundBook).isEqualTo(book);
//         assertThat(foundBook.getAuthors()).containsExactly(author1, author2);
//     }

// }


