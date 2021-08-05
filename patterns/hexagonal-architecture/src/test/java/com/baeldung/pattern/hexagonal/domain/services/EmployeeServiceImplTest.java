package com.baeldung.pattern.hexagonal.domain.services;

import com.baeldung.pattern.hexagonal.domain.models.Employee;
import com.baeldung.pattern.hexagonal.domain.ports.EmployeeRepository;
import com.baeldung.pattern.hexagonal.domain.ports.EmployeeService;
import com.baeldung.pattern.hexagonal.domain.ports.NotFoundException;
import com.baeldung.pattern.hexagonal.domain.ports.RepositoryException;
import com.baeldung.pattern.hexagonal.domain.ports.ServiceException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

/**
 * This is the test for the domain's business logic.
 * We only have to mock the ports used by the domain.
 */
@SpringBootTest(classes = EmployeeServiceImpl.class)
public class EmployeeServiceImplTest {

    @MockBean
    EmployeeRepository repository;
    @Autowired
    EmployeeService service;

    @Nested
    class AddEmployeeTests {

        @Test
        void whenAddingAnEmployee_thenReturnTheEmployeeFromDataAccess() throws RepositoryException, ServiceException {
            Employee incoming = new Employee();
            Employee outgoing = new Employee();
            when(repository.add(incoming)).thenReturn(outgoing);

            Employee result = service.addEmployee(incoming);

            assertThat(result).isSameAs(outgoing);
        }

        @Test
        void givenTheDataAccessOccursError_whenAddingAnEmployee_thenThrowServiceException() throws RepositoryException {
            Employee incoming = new Employee();
            RepositoryException ex = new RepositoryException();
            when(repository.add(incoming)).thenThrow(ex);

            assertThatThrownBy(()->service.addEmployee(incoming))
              .isInstanceOf(ServiceException.class)
              .hasCause(ex); // test for exception wrapping
        }

    }

    @Nested
    class GetEmployeeTests {

        @Test
        void whenSearchingAnEmployee_thenReturnTheEmployeeFromDataAccess() throws RepositoryException, ServiceException, NotFoundException {
            long id = 5L;
            Employee outgoing = new Employee();
            when(repository.findById(id)).thenReturn(Optional.of(outgoing));

            Employee result = service.getEmployee(id);

            assertThat(result).isSameAs(outgoing);
        }

        @Test
        void givenTheDataAccessOccursError_whenSearchingAnEmployee_thenThrowServiceException() throws RepositoryException {
            long id = 5L;
            RepositoryException ex = new RepositoryException();
            when(repository.findById(id)).thenThrow(ex);

            assertThatThrownBy(()->service.getEmployee(id))
              .isInstanceOf(ServiceException.class)
              .hasCause(ex); // test for exception wrapping
        }

        @Test
        void givenTheEmployeeDoesNotExist_whenSearchingAnEmployee_thenThrowNotFoundException() throws RepositoryException {
            long id = 5L;
            when(repository.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(()->service.getEmployee(id))
              .isInstanceOf(NotFoundException.class);
        }

    }

}
