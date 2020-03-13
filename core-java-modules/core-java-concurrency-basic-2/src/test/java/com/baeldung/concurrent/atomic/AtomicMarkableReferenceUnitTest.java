package com.baeldung.concurrent.atomic;

import java.util.concurrent.atomic.AtomicMarkableReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AtomicMarkableReferenceUnitTest {

    private AtomicMarkableReference<Employee> employeeNode;

    class Employee {
        private int id;
        private String name;

        Employee(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }       
    }

    @Test
    void givenMarkValueAsTrue_whenUsingIsMarkedMethod_thenMarkValueShouldBeTrue() {
        employeeNode = new AtomicMarkableReference<Employee>(new Employee(123, "Mike"), true);

        Assertions.assertTrue(employeeNode.isMarked());
    }

    @Test
    void givenMarkValueAsFalse_whenUsingIsMarkedMethod_thenMarkValueShouldBeFalse() {
        employeeNode = new AtomicMarkableReference<Employee>(new Employee(123, "Mike"), false);

        Assertions.assertFalse(employeeNode.isMarked());
    }

    @Test
    void whenUsingGetReferenceMethod_thenCurrentReferenceShouldBeReturned() {
        Employee employee = new Employee(123, "Mike");
        employeeNode = new AtomicMarkableReference<Employee>(employee, false);

        Assertions.assertEquals(employeeNode.getReference(), employee);
    }

    @Test
    void whenUsingGetMethod_thenCurrentReferenceAndMarkShouldBeReturned() {
        Employee employee = new Employee(123, "Mike");
        employeeNode = new AtomicMarkableReference<Employee>(employee, true);

        boolean[] markHolder = new boolean[1];
        Employee currentEmployee = employeeNode.get(markHolder);

        Assertions.assertEquals(currentEmployee, employee);
        Assertions.assertTrue(markHolder[0]);
    }

    @Test
    void givenNewReferenceAndMark_whenUsingSetMethod_thenCurrentReferenceAndMarkShouldBeUpdated() {
        Employee employee = new Employee(123, "Mike");
        employeeNode = new AtomicMarkableReference<Employee>(employee, true);

        Employee newEmployee = new Employee(124, "John");
        employeeNode.set(newEmployee, false);

        Assertions.assertEquals(employeeNode.getReference(), newEmployee);
        Assertions.assertFalse(employeeNode.isMarked());
    }

    @Test
    void givenTheSameObjectReference_whenUsingAttemptMarkMethod_thenMarkShouldBeUpdated() {
        Employee employee = new Employee(123, "Mike");
        employeeNode = new AtomicMarkableReference<Employee>(employee, true);

        Assertions.assertTrue(employeeNode.attemptMark(employee, false));
        Assertions.assertFalse(employeeNode.isMarked());
    }

    @Test
    void givenDifferentObjectReference_whenUsingAttemptMarkMethod_thenMarkShouldNotBeUpdated() {
        Employee employee = new Employee(123, "Mike");
        Employee expectedEmployee = new Employee(123, "Mike");
        employeeNode = new AtomicMarkableReference<Employee>(employee, true);

        Assertions.assertFalse(employeeNode.attemptMark(expectedEmployee, false));
        Assertions.assertTrue(employeeNode.isMarked());
    }

    @Test
    void givenCurrentReferenceAndCurrentMark_whenUsingCompareAndSet_thenReferenceAndMarkShouldBeUpdated() {
        Employee employee = new Employee(123, "Mike");
        employeeNode = new AtomicMarkableReference<Employee>(employee, true);
        Employee newEmployee = new Employee(124, "John");

        Assertions.assertTrue(employeeNode.compareAndSet(employee, newEmployee, true, false));
        Assertions.assertEquals(employeeNode.getReference(), newEmployee);
        Assertions.assertFalse(employeeNode.isMarked());
    }

    @Test
    void givenNotCurrentReferenceAndCurrentMark_whenUsingCompareAndSet_thenReferenceAndMarkShouldNotBeUpdated() {
        Employee employee = new Employee(123, "Mike");
        employeeNode = new AtomicMarkableReference<Employee>(employee, true);
        Employee newEmployee = new Employee(124, "John");

        Assertions.assertFalse(employeeNode.compareAndSet(new Employee(1234, "Steve"), newEmployee, true, false));
        Assertions.assertEquals(employeeNode.getReference(), employee);
        Assertions.assertTrue(employeeNode.isMarked());
    }

    @Test
    void givenCurrentReferenceAndNotCurrentMark_whenUsingCompareAndSet_thenReferenceAndMarkShouldNotBeUpdated() {
        Employee employee = new Employee(123, "Mike");
        employeeNode = new AtomicMarkableReference<Employee>(employee, true);
        Employee newEmployee = new Employee(124, "John");

        Assertions.assertFalse(employeeNode.compareAndSet(employee, newEmployee, false, true));
        Assertions.assertEquals(employeeNode.getReference(), employee);
        Assertions.assertTrue(employeeNode.isMarked());
    }

    @Test
    void givenNotCurrentReferenceAndNotCurrentMark_whenUsingCompareAndSet_thenReferenceAndMarkShouldNotBeUpdated() {
        Employee employee = new Employee(123, "Mike");
        employeeNode = new AtomicMarkableReference<Employee>(employee, true);
        Employee newEmployee = new Employee(124, "John");

        Assertions.assertFalse(employeeNode.compareAndSet(new Employee(1234, "Steve"), newEmployee, false, true));
        Assertions.assertEquals(employeeNode.getReference(), employee);
        Assertions.assertTrue(employeeNode.isMarked());
    }

    @Test
    void givenCurrentReferenceAndCurrentMark_whenUsingWeakCompareAndSet_thenReferenceAndMarkShouldBeUpdated() {
        Employee employee = new Employee(123, "Mike");
        employeeNode = new AtomicMarkableReference<Employee>(employee, true);
        Employee newEmployee = new Employee(124, "John");

        Assertions.assertTrue(employeeNode.weakCompareAndSet(employee, newEmployee, true, false));
        Assertions.assertEquals(employeeNode.getReference(), newEmployee);
        Assertions.assertFalse(employeeNode.isMarked());
    }

    @Test
    void givenNotCurrentReferenceAndCurrentMark_whenUsingWeakCompareAndSet_thenReferenceAndMarkShouldNotBeUpdated() {
        Employee expectedEmployee = new Employee(123, "Mike");
        employeeNode = new AtomicMarkableReference<Employee>(expectedEmployee, true);
        Employee newEmployee = new Employee(124, "John");

        Assertions.assertFalse(employeeNode.weakCompareAndSet(new Employee(1234, "Steve"), newEmployee, true, false));
        Assertions.assertEquals(employeeNode.getReference(), expectedEmployee);
        Assertions.assertTrue(employeeNode.isMarked());
    }

    @Test
    void givenCurrentReferenceAndNotCurrentMark_whenUsingWeakCompareAndSet_thenReferenceAndMarkShouldNotBeUpdated() {
        Employee expectedEmployee = new Employee(123, "Mike");
        employeeNode = new AtomicMarkableReference<Employee>(expectedEmployee, true);
        Employee newEmployee = new Employee(124, "John");

        Assertions.assertFalse(employeeNode.weakCompareAndSet(expectedEmployee, newEmployee, false, true));
        Assertions.assertEquals(employeeNode.getReference(), expectedEmployee);
        Assertions.assertTrue(employeeNode.isMarked());
    }

    @Test
    void givenNotCurrentReferenceAndNotCurrentMark_whenUsingWeakCompareAndSet_thenReferenceAndMarkShouldNotBeUpdated() {
        Employee employee = new Employee(123, "Mike");
        employeeNode = new AtomicMarkableReference<Employee>(employee, true);
        Employee newEmployee = new Employee(124, "John");

        Assertions.assertFalse(employeeNode.weakCompareAndSet(new Employee(1234, "Steve"), newEmployee, false, true));
        Assertions.assertEquals(employeeNode.getReference(), employee);
        Assertions.assertTrue(employeeNode.isMarked());
    }
}
