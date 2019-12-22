package com.baeldung.osiv;

import com.baeldung.osiv.model.BasicUser;
import com.baeldung.osiv.repository.BasicUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ContextConfiguration(classes = OsivApplication.class)
class UserControllerIntegrationTest {

    @Autowired
    private BasicUserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        BasicUser user = new BasicUser();
        user.setUsername("root");
        user.setPermissions(new HashSet<>(Arrays.asList("PERM_READ", "PERM_WRITE")));

        userRepository.save(user);
    }

    @Test
    void givenTheUserExists_WhenOsivIsEnabled_ThenLazyInitWorkEverywhere() throws Exception {
        mockMvc.perform(get("/users/root"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("root"))
                .andExpect(jsonPath("$.permissions", containsInAnyOrder("PERM_READ", "PERM_WRITE")));
    }

    @AfterEach
    void flushDb() {
        userRepository.deleteAll();
    }
}
