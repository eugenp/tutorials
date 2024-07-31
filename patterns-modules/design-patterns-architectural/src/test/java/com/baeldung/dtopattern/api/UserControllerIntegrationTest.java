package com.baeldung.dtopattern.api;

import com.baeldung.dtopattern.domain.Role;
import com.baeldung.dtopattern.domain.RoleRepository;
import com.baeldung.dtopattern.domain.User;
import com.baeldung.dtopattern.domain.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @LocalServerPort
    int port;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_shouldReturnUseId() throws Exception {
        UserCreationDTO request = new UserCreationDTO();
        request.setName("User 1");
        request.setPassword("Test@123456");
        request.setRoles(Collections.singletonList("admin"));

        given()
            .contentType(ContentType.JSON)
            .body(objectMapper.writeValueAsString(request))
        .when()
            .port(port)
            .post("/users")
        .then()
            .statusCode(OK.value())
            .body("id", notNullValue());
    }

    @Test
    void getAll_shouldReturnUseDTO() {

        userRepository.deleteAll();

        String roleName = "admin";
        Role admin = new Role(roleName);
        roleRepository.save(admin);

        String name = "User 1";
        User user = new User(name, "Test@123456", Collections.singletonList(admin));
        userRepository.save(user);

        given()
            .port(port)
        .when()
            .get("/users")
        .then()
            .statusCode(OK.value())
            .body("size()", is(1))
            .body("[0].name", equalTo(name))
            .body("[0].roles", hasItem(roleName));
    }
}
