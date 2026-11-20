package com.baeldung.multiparameterconstructor;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.ConstructorDetector;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

public class JacksonMultiParameterConstructorUnitTest {

    @Test
    public void givenATicket_whenDeserializedFromJson_thenOriginalAndDeserializedTicketAreEqual() throws JsonProcessingException {
        Ticket ticket = new Ticket("Devoxx", "Maria Monroe", Currency.GBP, 50);

        ObjectMapper mapper = JsonMapper.builder()
            .constructorDetector(ConstructorDetector.USE_PROPERTIES_BASED)
            .addModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES))
            .build();

        String json = mapper.writeValueAsString(ticket);

        Ticket deserializedTicket = mapper.readValue(json, Ticket.class);

        assertThat(deserializedTicket).isEqualTo(ticket);
    }

    @Test
    public void givenACurrency_whenDeserializedFromJson_thenOriginalAndDeserializedCurrencyAreEqual() throws JsonProcessingException {

        Currency currency = Currency.EUR;

        ObjectMapper mapper = JsonMapper.builder()
            .constructorDetector(ConstructorDetector.USE_PROPERTIES_BASED)
            .addModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES))
            .build();

        String json = mapper.writeValueAsString(currency);

        Currency deserializedCurrency = mapper.readValue(json, Currency.class);

        assertThat(deserializedCurrency).isEqualTo(currency);
    }

    @Test
    public void givenAGuest_whenDeserializedFromJson_thenOriginalAndDeserializedGuestAreEqual() throws JsonProcessingException {

        Guest guest = new Guest("Maria", "Monroe");

        ObjectMapper mapper = JsonMapper.builder()
            .constructorDetector(ConstructorDetector.USE_PROPERTIES_BASED)
            .addModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES))
            .build();

        String json = mapper.writeValueAsString(guest);

        Guest deserializedGuest = mapper.readValue(json, Guest.class);

        assertThat(deserializedGuest).isEqualTo(guest);
    }
}
