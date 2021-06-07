
import com.baeldung.hexgonal.repo.EmployeeRepository;
import com.baeldung.hexgonal.services.EmployeeService;
import com.baeldung.hexgonal.services.EmployeeServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Random;


@ExtendWith(MockitoExtension.class)
class EmployeeServiceUnitTest {

    @Mock
    private EmployeeRepository employeeRepository;

    private EmployeeService employeeService;

    @BeforeEach
    void initialize() {
        MockitoAnnotations.initMocks(this);
        employeeService = EmployeeServiceFactory.getInstance(employeeRepository);
    }

    @Test
    void givenValidEmployeeParamsAreProvided_thenEmployeeIsSavedInTheRepository() {
        Long id = new Random().nextLong();
        Long employeeId = employeeService.createEmployee(id,"David","Brooks","12345");
        Mockito.verify(employeeRepository, Mockito.times(1))
            .save(Mockito.any());
        Assertions.assertNotNull(employeeId);
    }

    @Test
    void givenExistingEmployeeId_thenEmployeeIsDeletedFromTheRepository() {
        Long id = new Random().nextLong();
        employeeService.deleteEmployee(id);
        Mockito.verify(employeeRepository, Mockito.times(1))
                .delete(id);
    }

}
