package com.baeldung.mongoauth;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.baeldung.mongoauth.domain.Role;
import com.baeldung.mongoauth.domain.User;
import com.baeldung.mongoauth.domain.UserRole;

@SpringBootTest(classes = { MongoAuthApplication.class })
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MongoAuthApplicationIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private MockMvc mvc;

    private static final String USER_NAME = "user@gmail.com";
    private static final String ADMIN_NAME = "admin@gmail.com";
    private static final String PASSWORD = "password";

    @BeforeEach
    public void setup() {

        setUp();

        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    private void setUp() {
        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");
        mongoTemplate.save(roleUser);

        User user = new User();
        user.setUsername(USER_NAME);
        user.setPassword(bCryptPasswordEncoder.encode(PASSWORD));

        UserRole userRole = new UserRole();
        userRole.setRole(roleUser);
        user.setUserRoles(new HashSet<>(Collections.singletonList(userRole)));
        mongoTemplate.save(user);

        User admin = new User();
        admin.setUsername(ADMIN_NAME);
        admin.setPassword(bCryptPasswordEncoder.encode(PASSWORD));

        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");
        mongoTemplate.save(roleAdmin);

        UserRole adminRole = new UserRole();
        adminRole.setRole(roleAdmin);
        admin.setUserRoles(new HashSet<>(Collections.singletonList(adminRole)));
        mongoTemplate.save(admin);
    }

    @Test
    void givenUserCredentials_whenInvokeUserAuthorizedEndPoint_thenReturn200() throws Exception {
        mvc.perform(get("/user").with(httpBasic(USER_NAME, PASSWORD)))
                .andExpect(status().isOk());
    }

    @Test
    void givenUserNotExists_whenInvokeEndPoint_thenReturn401() throws Exception {
        mvc.perform(get("/user").with(httpBasic("not_existing_user", "password")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void givenUserExistsAndWrongPassword_whenInvokeEndPoint_thenReturn401() throws Exception {
        mvc.perform(get("/user").with(httpBasic(USER_NAME, "wrong_password")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void givenUserCredentials_whenInvokeAdminAuthorizedEndPoint_thenReturn403() throws Exception {
        mvc.perform(get("/admin").with(httpBasic(USER_NAME, PASSWORD)))
                .andExpect(status().isForbidden());
    }

    @Test
    void givenAdminCredentials_whenInvokeAdminAuthorizedEndPoint_thenReturn200() throws Exception {
        mvc.perform(get("/admin").with(httpBasic(ADMIN_NAME, PASSWORD)))
                .andExpect(status().isOk());

        mvc.perform(get("/user").with(httpBasic(ADMIN_NAME, PASSWORD)))
                .andExpect(status().isOk());
    }

}
