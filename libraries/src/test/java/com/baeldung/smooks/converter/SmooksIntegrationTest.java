package com.baeldung.smooks.converter;

import com.baeldung.smooks.model.Item;
import com.baeldung.smooks.model.Order;
import com.baeldung.smooks.model.Status;
import com.baeldung.smooks.model.Supplier;
import org.junit.Test;
import org.milyn.validation.ValidationResult;
import java.text.SimpleDateFormat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class SmooksIntegrationTest {

    private static final String EDIFACT_MESSAGE = "UNA:+.? '\r\n" + "UNH+771+IN_PROGRESS+2018-01-14'\r\n" + "CTA+CompanyX+1234567'\r\n" + "LIN+1+PX1234+9.99'\r\n" + "LIN+2+RX990+120.32'\r\n";
    private static final String EMAIL_MESSAGE = "Hi,\r\n" + "Order number #771 created on 2018-01-14 is currently in IN_PROGRESS status.\r\n" + "Consider contact supplier \"CompanyX\"  with phone number: \"1234567\".\r\n" + "Order items:\r\n"
            + "1  X PX1234 (total price 9.99)\r\n" + "2  X RX990 (total price 240.64)\r\n";

    @Test
    public void givenOrderXML_whenConvert_thenPOJOsConstructedCorrectly() throws Exception {

        OrderConverter xmlToJavaOrderConverter = new OrderConverter();
        Order order = xmlToJavaOrderConverter.convertOrderXMLToOrderObject("/smooks/order.xml");

        assertThat(order.getNumber(), is(771L));
        assertThat(order.getStatus(), is(Status.IN_PROGRESS));
        assertThat(order.getCreationDate(), is(new SimpleDateFormat("yyyy-MM-dd").parse("2018-01-14")));
        assertThat(order.getSupplier(), is(new Supplier("CompanyX", "1234567")));
        assertThat(order.getItems(), containsInAnyOrder(new Item("PX1234", 9.99, 1), new Item("RX990", 120.32, 2)));

    }

    @Test
    public void givenIncorrectOrderXML_whenValidate_thenExpectValidationErrors() throws Exception {
        OrderValidator orderValidator = new OrderValidator();
        ValidationResult validationResult = orderValidator.validate("/smooks/order.xml");

        assertThat(validationResult.getErrors(), hasSize(1));
        // 1234567 didn't match ^[0-9\\-\\+]{9,15}$
        assertThat(validationResult.getErrors().get(0).getFailRuleResult().getRuleName(), is("supplierPhone"));
    }

    @Test
    public void givenOrderXML_whenApplyEDITemplate_thenConvertedToEDIFACT() throws Exception {
        OrderConverter orderConverter = new OrderConverter();
        String edifact = orderConverter.convertOrderXMLtoEDIFACT("/smooks/order.xml");
        assertThat(edifact, is(EDIFACT_MESSAGE));
    }

    @Test
    public void givenOrderXML_whenApplyEmailTemplate_thenConvertedToEmailMessage() throws Exception {
        OrderConverter orderConverter = new OrderConverter();
        String emailMessage = orderConverter.convertOrderXMLtoEmailMessage("/smooks/order.xml");
        assertThat(emailMessage, is(EMAIL_MESSAGE));
    }
}