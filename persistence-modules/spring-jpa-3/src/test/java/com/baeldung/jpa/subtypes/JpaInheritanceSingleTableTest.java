package com.baeldung.jpa.subtypes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.jpa.subtypes.entity.ContractEmployee;
import com.baeldung.jpa.subtypes.entity.Employee;
import com.baeldung.jpa.subtypes.entity.PermanentEmployee;
import com.baeldung.jpa.subtypes.repository.ContractEmployeeRepository;
import com.baeldung.jpa.subtypes.repository.EmployeeRepository;
import com.baeldung.jpa.subtypes.repository.PermanentEmployeeRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SubTypeJpaApplication.class)
class JpaInheritanceSingleTableTest {

    @Autowired
    EmployeeRepository empRepository;

    @Autowired
    PermanentEmployeeRepository permEmpRepository;

    @Autowired
    ContractEmployeeRepository contrEmpRepository;

    @BeforeEach
    void setup() {
        PermanentEmployee perm1 = new PermanentEmployee(10, "John", 48);
        PermanentEmployee perm2 = new PermanentEmployee(11, "Sam", 30);
        PermanentEmployee perm3 = new PermanentEmployee(12, "Vinny", 25);

        permEmpRepository.save(perm1);
        permEmpRepository.save(perm2);
        permEmpRepository.save(perm3);

        ContractEmployee contr1 = new ContractEmployee(30, "Mitchell", 23);
        ContractEmployee contr2 = new ContractEmployee(180, "Alvin", 39);
        ContractEmployee contr3 = new ContractEmployee(365, "Greg", 41);

        contrEmpRepository.save(contr1);
        contrEmpRepository.save(contr2);
        contrEmpRepository.save(contr3);

    }

    @AfterEach
    void tearDown() {
        permEmpRepository.deleteAll();
        contrEmpRepository.deleteAll();
    }

    @Test
    void givenRecordsInserted_whenPermEmployeesQueried_thenOnlyPermEmployeeRecordsReturned() {
        List<PermanentEmployee> perEmpList = permEmpRepository.findAll();
        assertThat(perEmpList.size()).isEqualTo(3);
    }

    @Test
    void givenRecordsInserted_whenContrEmployeesQueried_thenOnlyContrEmployeeRecordsReturned() {
        List<ContractEmployee> contrList = contrEmpRepository.findAll();
        assertThat(contrList.size()).isEqualTo(3);
    }
    
    @Test
    void givenRecordsInserted_whenAllEmployeesQueried_thenAllEmployeeRecordsReturned() {
        List<Employee> empList = empRepository.findAll();
        assertThat(empList.size()).isEqualTo(6);
    }
    
    @Test
    void givenRecordsInserted_whenDiscriminatorQueryUsed_thenEmployeeRecordsByTypeReturned() {
        List<ContractEmployee> contrList = empRepository.filterContract(150,"M");
        List<PermanentEmployee> perEmpList = empRepository.filterPermanent(11,"Jo");
        
        assertThat(contrList.size()).isEqualTo(1);
        assertThat(perEmpList.size()).isEqualTo(1);
    }
}
