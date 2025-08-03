package com.baeldung.bindcustomvalidationmessage.integrationtest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.baeldung.bindcustomvalidationmessage.GlobalExceptionHandler;
import com.baeldung.bindcustomvalidationmessage.UserController;
import com.baeldung.bindcustomvalidationmessage.externalfilevalidation.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class UserControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        UserController controller = new UserController();

        LocalValidatorFactoryBean validatorFactory = new LocalValidatorFactoryBean();
        validatorFactory.afterPropertiesSet();

        this.mockMvc =
            MockMvcBuilders.standaloneSetup(controller)
                .setValidator(validatorFactory)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        this.objectMapper = new ObjectMapper();
    }

    @Test
    void whenAllFieldsValid_thenNoValidationErrors() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setName("John Doe");
        dto.setEmail("john@example.com");

        mockMvc
            .perform(
                post("/users")
                    .characterEncoding("UTF-8")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("\"User created successfully\""));
    }

    @Test
    void whenNameIsBlank_thenReturnsValidationError() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setName(""); // invalid
        dto.setEmail("john@example.com");

        mockMvc
            .perform(
                post("/users")
                    .characterEncoding("UTF-8")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(
                jsonPath("$.name")
                    .value("User name must be at least 2 characters long"));
    }

    @Test
    void whenNameIsNull_thenReturnsValidationError() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setName(null); // invalid
        dto.setEmail("john@example.com");

        mockMvc
            .perform(
                post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void whenNameTooShort_thenReturnsValidationError() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setName("A"); // invalid
        dto.setEmail("john@example.com");

        mockMvc
            .perform(
                post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void whenEmailIsInvalid_thenReturnsValidationError() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setName("Valid Name");
        dto.setEmail("not-an-email");

        mockMvc
            .perform(
                post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void whenEmailIsNull_thenReturnsValidationError() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setName("Valid Name");
        dto.setEmail(null);

        mockMvc
            .perform(
                post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
    }
}
