package com.bealdung.onboarding;
import com.bealdung.onboarding.adapters.persistence.EmployeeEntity;
import com.bealdung.onboarding.adapters.persistence.EmployeeRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.net.URISyntaxException;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeLiveTest {
    @Autowired
    EmployeeRepository employeeRepository;
    @LocalServerPort
    private int port;
    @Test
    void givenSavedEmployee_whenGetEmployeeInfo_thenCompareInfoAndDeleteEmployee() throws URISyntaxException {
        EmployeeEntity entity = new EmployeeEntity("Sarah", "Xyz");
        EmployeeEntity savedEntity = employeeRepository.save(entity);
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "http://localhost:" + port + "/employee/find?id=" + savedEntity.getId().toString();
        URI uri = new URI(baseUrl);
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        Assert.assertEquals(result.getBody(), "name : Sarah family : Xyz ID : " + savedEntity.getId());
        employeeRepository.delete(entity);
    }
}
