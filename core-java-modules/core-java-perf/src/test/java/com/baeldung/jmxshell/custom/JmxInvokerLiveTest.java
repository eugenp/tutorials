package com.baeldung.jmxshell.custom;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class JmxInvokerLiveTest {

    private static final String JMX_URL = "service:jmx:rmi:///jndi/rmi://localhost:11234/jmxrmi";
    private static final String JMX_MBEAN_NAME = "com.baeldung.jxmshell:name=calculator,type=basic";
    private static final String ATTRIBUTE_A = "A";
    private static final String ATTRIBUTE_B = "B";
    private static final String SUM_OPERATION = "sum";
    private static final Integer ATTRIBUTE_VALUE = 1;

    @Test
    @Order(1)
    void givenAttributeValue_whenSetAttributeA_thenResultMatches() {
        String attributeValue = ATTRIBUTE_VALUE.toString();

        String result = JmxInvoker.execute(JMX_URL, JMX_MBEAN_NAME, ATTRIBUTE_A, attributeValue);

        assertEquals(ATTRIBUTE_A + "=" + attributeValue, result);
    }

    @Test
    @Order(2)
    void givenAttributeValue_whenSetAttributeB_thenResultMatches() {
        String attributeValue = ATTRIBUTE_VALUE.toString();

        String result = JmxInvoker.execute(JMX_URL, JMX_MBEAN_NAME, ATTRIBUTE_B, attributeValue);

        assertEquals(ATTRIBUTE_B + "=" + attributeValue, result);
    }

    @Test
    @Order(3)
    void whenSumOperation_thenSumIsCorrect() {
        String result = JmxInvoker.execute(JMX_URL, JMX_MBEAN_NAME, SUM_OPERATION, null);

        assertEquals(SUM_OPERATION + "(): " + (ATTRIBUTE_VALUE + ATTRIBUTE_VALUE), result);
    }
}
