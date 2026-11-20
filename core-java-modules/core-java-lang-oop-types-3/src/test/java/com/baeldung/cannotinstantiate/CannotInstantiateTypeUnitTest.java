package com.baeldung.cannotinstantiate;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.baeldung.cannotinstantiate.sealed.BankTransfer;
import com.baeldung.cannotinstantiate.sealed.Circle;
import com.baeldung.cannotinstantiate.sealed.CreditCardPayment;
import com.baeldung.cannotinstantiate.sealed.Payment;
import com.baeldung.cannotinstantiate.sealed.Rectangle;
import com.baeldung.cannotinstantiate.sealed.Shape;
import com.baeldung.cannotinstantiate.sealed.Triangle;

class CannotInstantiateTypeUnitTest {

    @Test
    void whenUsingConcreteListImplementation_thenInstantiationSucceeds() {
        List<String> list = new ArrayList<>();

        list.add("item");

        assertEquals(1, list.size());
    }

    @Test
    void whenUsingConcreteMapImplementation_thenInstantiationSucceeds() {
        Map<String, Integer> scores = new HashMap<>();

        scores.put("Alice", 95);

        assertEquals(95, scores.get("Alice"));
    }

    @Test
    void whenUsingConcreteSetImplementation_thenInstantiationSucceeds() {
        Set<String> names = new HashSet<>();

        names.add("Alice");

        assertTrue(names.contains("Alice"));
    }

    @Test
    void whenUsingConcreteSubclass_thenAbstractClassInstantiationSucceeds() {
        DataExporter exporter = new CsvExporter();
        List<String> data = List.of("row1", "row2");

        exporter.export(data);

        assertInstanceOf(CsvExporter.class, exporter);
    }

    @Test
    void whenReferencingEnumConstant_thenEnumUsageSucceeds() {
        Status status = Status.ACTIVE;

        assertEquals(Status.ACTIVE, status);
    }

    @Test
    void whenUsingSealedInterfaceImplementation_thenInstantiationSucceeds() {
        Shape circle = new Circle(5.0);
        Shape rectangle = new Rectangle(4.0, 3.0);
        Shape triangle = new Triangle(6.0, 2.0);

        assertEquals(Math.PI * 25, circle.area(), 0.001);
        assertEquals(12.0, rectangle.area(), 0.001);
        assertEquals(6.0, triangle.area(), 0.001);
    }

    @Test
    void whenUsingSealedClassSubclass_thenInstantiationSucceeds() {
        Payment payment = new CreditCardPayment(100.0);
        Payment transfer = new BankTransfer(250.0);

        assertInstanceOf(CreditCardPayment.class, payment);
        assertInstanceOf(BankTransfer.class, transfer);
    }
}
