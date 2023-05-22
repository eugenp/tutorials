package com.baeldung.spring.data.jpa.paging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceUnitTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private static final List<Customer> ALL_CUSTOMERS = Arrays.asList(
      new Customer("Ali"), new Customer("Brian"),
      new Customer("Coddy"), new Customer("Di"),
      new Customer("Eve"), new Customer("Fin"),
      new Customer("Grace"), new Customer("Harry"),
      new Customer("Ivan"), new Customer("Judy"),
      new Customer("Kasim"), new Customer("Liam"),
      new Customer("Mike"), new Customer("Nick"),
      new Customer("Omar"), new Customer("Penny"),
      new Customer("Queen"), new Customer("Rob"),
      new Customer("Sue"), new Customer("Tammy"));

    private static final List<String> PAGE_1_CONTENTS = Arrays.asList("Ali", "Brian", "Coddy", "Di", "Eve");

    private static final List<String> PAGE_2_CONTENTS = Arrays.asList("Fin", "Grace", "Harry", "Ivan", "Judy");

    private static final List<String> PAGE_3_CONTENTS = Arrays.asList("Kasim", "Liam", "Mike", "Nick", "Omar");

    private static final List<String> PAGE_4_CONTENTS = Arrays.asList("Penny", "Queen", "Rob", "Sue", "Tammy");

    private static final List<String> EMPTY_PAGE = Arrays.asList();

    @BeforeEach
    void setup() {
        when(customerRepository.findAll()).thenReturn(ALL_CUSTOMERS);
    }

    private static Collection<Object[]> testIO() {
        return Arrays.asList(new Object[][] {
          { 0, 5, PAGE_1_CONTENTS, 20L, 4L },
          { 1, 5, PAGE_2_CONTENTS, 20L, 4L },
          { 2, 5, PAGE_3_CONTENTS, 20L, 4L },
          { 3, 5, PAGE_4_CONTENTS, 20L, 4L },
          { 4, 5, EMPTY_PAGE, 20L, 4L } }
        );
    }

    @ParameterizedTest
    @MethodSource("testIO")
    void givenAListOfCustomers_whenGetCustomers_thenReturnsDesiredDataAlongWithPagingInformation(int page, int size, List<String> expectedNames, long expectedTotalElements, long expectedTotalPages) {
        Page<Customer> customers = customerService.getCustomers(page, size);
        List<String> names = customers.getContent()
          .stream()
          .map(Customer::getName)
          .collect(Collectors.toList());

        assertEquals(expectedNames.size(), names.size());
        assertEquals(expectedNames, names);
        assertEquals(expectedTotalElements, customers.getTotalElements());
        assertEquals(expectedTotalPages, customers.getTotalPages());
    }
}
