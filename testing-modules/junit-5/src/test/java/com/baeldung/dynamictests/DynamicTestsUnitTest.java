package com.baeldung.dynamictests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.ThrowingConsumer;


public class DynamicTestsUnitTest {

    @TestFactory
    Collection<DynamicTest> dynamicTestsWithCollection() {
        return Arrays.asList(DynamicTest.dynamicTest("Add test", () -> assertEquals(2, Math.addExact(1, 1))), DynamicTest.dynamicTest("Multiply Test", () -> assertEquals(4, Math.multiplyExact(2, 2))));
    }

    @TestFactory
    Iterable<DynamicTest> dynamicTestsWithIterable() {
        return Arrays.asList(DynamicTest.dynamicTest("Add test", () -> assertEquals(2, Math.addExact(1, 1))), DynamicTest.dynamicTest("Multiply Test", () -> assertEquals(4, Math.multiplyExact(2, 2))));
    }

    @TestFactory
    Iterator<DynamicTest> dynamicTestsWithIterator() {
        return Arrays.asList(DynamicTest.dynamicTest("Add test", () -> assertEquals(2, Math.addExact(1, 1))), DynamicTest.dynamicTest("Multiply Test", () -> assertEquals(4, Math.multiplyExact(2, 2))))
            .iterator();
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestsFromIntStream() {
        return IntStream.iterate(0, n -> n + 2)
            .limit(10)
            .mapToObj(n -> DynamicTest.dynamicTest("test" + n, () -> assertTrue(n % 2 == 0)));
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestsFromStream() {

        // sample input and output
        List<String> inputList = Arrays.asList("www.somedomain.com", "www.anotherdomain.com", "www.yetanotherdomain.com");
        List<String> outputList = Arrays.asList("154.174.10.56", "211.152.104.132", "178.144.120.156");

        // input generator that generates inputs using inputList
        Iterator<String> inputGenerator = inputList.iterator();

        // a display name generator that creates a different name based on the input
        Function<String, String> displayNameGenerator = (input) -> "Resolving: " + input;

        // the test executor, which actually has the logic of how to execute the test case
        DomainNameResolver resolver = new DomainNameResolver();
        ThrowingConsumer<String> testExecutor = (input) -> {
            int id = inputList.indexOf(input);
            assertEquals(outputList.get(id), resolver.resolveDomain(input));
        };

        // combine everything and return a Stream of DynamicTest
        return DynamicTest.stream(inputGenerator, displayNameGenerator, testExecutor);
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestsFromStreamInJava8() {

        DomainNameResolver resolver = new DomainNameResolver();

        List<String> inputList = Arrays.asList("www.somedomain.com", "www.anotherdomain.com", "www.yetanotherdomain.com");
        List<String> outputList = Arrays.asList("154.174.10.56", "211.152.104.132", "178.144.120.156");

        return inputList.stream()
            .map(dom -> DynamicTest.dynamicTest("Resolving: " + dom, () -> {
                int id = inputList.indexOf(dom);
                assertEquals(outputList.get(id), resolver.resolveDomain(dom));
            }));

    }

    @TestFactory
    Stream<DynamicTest> dynamicTestsForEmployeeWorkflows() {
        List<Employee> inputList = Arrays.asList(new Employee(1, "Fred"), new Employee(2), new Employee(3, "John"));

        EmployeeDao dao = new EmployeeDao();
        Stream<DynamicTest> saveEmployeeStream = inputList.stream()
            .map(emp -> DynamicTest.dynamicTest("saveEmployee: " + emp.toString(), () -> {
                Employee returned = dao.save(emp.getId());
                assertEquals(returned.getId(), emp.getId());
            }));

        Stream<DynamicTest> saveEmployeeWithFirstNameStream = inputList.stream()
            .filter(emp -> !emp.getFirstName()
                .isEmpty())
            .map(emp -> DynamicTest.dynamicTest("saveEmployeeWithName" + emp.toString(), () -> {
                Employee returned = dao.save(emp.getId(), emp.getFirstName());
                assertEquals(returned.getId(), emp.getId());
                assertEquals(returned.getFirstName(), emp.getFirstName());
            }));

        return Stream.concat(saveEmployeeStream, saveEmployeeWithFirstNameStream);
    }

    class DomainNameResolver {

        private Map<String, String> ipByDomainName = new HashMap<>();

        DomainNameResolver() {
            this.ipByDomainName.put("www.somedomain.com", "154.174.10.56");
            this.ipByDomainName.put("www.anotherdomain.com", "211.152.104.132");
            this.ipByDomainName.put("www.yetanotherdomain.com", "178.144.120.156");
        }

        public String resolveDomain(String domainName) {
            return ipByDomainName.get(domainName);
        }
    }

}
