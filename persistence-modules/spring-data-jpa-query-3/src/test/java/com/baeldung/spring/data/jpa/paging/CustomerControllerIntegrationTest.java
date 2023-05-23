package com.baeldung.spring.data.jpa.paging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(CustomerRestController.class)
public class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @BeforeEach
    void setup() {
        List<Customer> allCustomers = getAllCustomers();
        Page<Customer> customerPage = new PageImpl<>(allCustomers, PageRequest.of(1, 5), allCustomers.size());

        when(customerService.getCustomers(1, 5)).thenReturn(customerPage);

    }

    private static List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        IntStream.range(0, 20)
          .forEach(i -> {
              Customer customer = new Customer((Integer.toString(i)));
              customers.add(customer);
          });
        return customers;
    }

    @Test
    void givenTotalCustomers20_whenGetRequestWithPageAndSize_thenPagedReponseIsReturnedFromDesiredPageAndSize() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/customers?page=1&size=5"))
          .andExpect(status().isOk())
          .andReturn();

        MockHttpServletResponse response = result.getResponse();

        JSONObject jsonObject = new JSONObject(response.getContentAsString());
        assertThat(jsonObject.get("totalPages")).isEqualTo(4);
        assertThat(jsonObject.get("totalElements")).isEqualTo(20);
        assertThat(jsonObject.get("number")).isEqualTo(1);
        assertThat(jsonObject.get("size")).isEqualTo(5);
        assertThat(jsonObject.get("content")).isNotNull();
    }
}
