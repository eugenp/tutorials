package com.baeldung.ignite.spring;

import com.baeldung.ignite.spring.config.SpringDataConfig;
import com.baeldung.ignite.spring.dto.EmployeeDTO;
import com.baeldung.ignite.spring.repository.EmployeeRepository;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * Created by Gebruiker on 4/12/2018.
 */
public class IgniteApp {

    public static void main (String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SpringDataConfig.class);
        context.refresh();

        EmployeeRepository repository = context.getBean(EmployeeRepository.class);

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1);
        employeeDTO.setName("John");
        employeeDTO.setEmployed(true);

        repository.save(employeeDTO.getId(), employeeDTO);

        EmployeeDTO employee = repository.getEmployeeDTOById(employeeDTO.getId());
        System.out.println(employee);
    }

    private void getUsingTheCache(Integer employeeId) {

        Ignite ignite = Ignition.ignite();

        IgniteCache<Integer, EmployeeDTO> cache = ignite.cache("baeldungCache");

        EmployeeDTO employeeDTO = cache.get(employeeId);

        System.out.println(employeeDTO);

        SqlFieldsQuery sql = new SqlFieldsQuery(
                "select * from EmployeeDTO where isEmployed = 'true'");

        QueryCursor<List<?>> cursor = cache.query(sql);
    }
}
