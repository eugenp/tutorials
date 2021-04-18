package com.baeldung.pattern.hexagonal.architecture.application;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.baeldung.pattern.hexagonal.architecture.HexagonalArchitectureApplication;
import com.baeldung.pattern.hexagonal.architecture.application.adapters.AddressQueueListener;
import com.baeldung.pattern.hexagonal.architecture.application.dto.AddressDto;
import com.baeldung.pattern.hexagonal.architecture.domain.model.Address;
import com.baeldung.pattern.hexagonal.architecture.infrastructure.adapters.DbAddressRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

@SpringBootTest(classes = { HexagonalArchitectureApplication.class })
@WebAppConfiguration
@TestPropertySource(locations = "classpath:application.properties")
class AddressQueueListenerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    AddressQueueListener listener;

    @Autowired
    DbAddressRepository dbAddressRepository;

    @Test
    void whenReceiveAddressDto_thenFindOneAddress() {
        // given
        AddressDto addressDto = createAddressDto();

        // when
        listener.receiveMessage(addressDto);

        // then
        List<Address> adresses = dbAddressRepository.findAll();
        Address result = adresses.get(0);
        assertEquals(1, adresses.size());
        assertEquals(result.getName(), addressDto.getName());
        assertEquals(result.getCity(), addressDto.getCity());
        assertEquals(result.getHouse(), addressDto.getHouse());
        assertEquals(result.getZip(), addressDto.getZip());
        assertEquals(result.getStreet(), addressDto.getStreet());
    }

    private AddressDto createAddressDto() {
        return AddressDto.builder()
            .id(1L)
            .name("palace")
            .city("Warsaw")
            .house("5")
            .street("Test")
            .zip("22-222")
            .build();
    }
}
