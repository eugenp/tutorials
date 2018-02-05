package com.stackify.stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

public class EmployeeTest {
    private static Employee[] arrayOfEmps = {
        new Employee(1, "Jeff Bezos", 100000.0), 
        new Employee(2, "Bill Gates", 200000.0), 
        new Employee(3, "Mark Zuckerberg", 300000.0)
    };

    private static List<Employee> empList = Arrays.asList(arrayOfEmps);
    private static EmployeeRepository employeeRepository = new EmployeeRepository(empList);

    @Test
    public void whenGetStreamFromList_ObtainStream() {
        assert(empList.stream() instanceof Stream<?>);
    }

    @Test
    public void whenGetStreamFromArray_ObtainStream() {
        assert(Stream.of(arrayOfEmps) instanceof Stream<?>);
    }

    @Test
    public void whenGetStreamFromElements_ObtainStream() {
        assert(Stream.of(arrayOfEmps[0], arrayOfEmps[1], arrayOfEmps[2]) instanceof Stream<?>);
    }

    @Test
    public void whenIncrementSalaryForEachEmployee_thenApplyNewSalary() {
        Employee[] arrayOfEmps = {
            new Employee(1, "Jeff Bezos", 100000.0), 
            new Employee(2, "Bill Gates", 200000.0), 
            new Employee(3, "Mark Zuckerberg", 300000.0)
        };

        List<Employee> empList = Arrays.asList(arrayOfEmps);
        
        empList.stream().forEach(e -> e.salaryIncrement(10.0));
        
        assertThat(empList, contains(
          hasProperty("salary", equalTo(110000.0)),
          hasProperty("salary", equalTo(220000.0)),
          hasProperty("salary", equalTo(330000.0))
        ));
    }

    @Test
    public void whenMapIdToEmployees_thenGetEmployeeStream() {
        Integer[] empIds = { 1, 2, 3 };
        
        List<Employee> employees = Stream.of(empIds)
          .map(employeeRepository::findById)
          .collect(Collectors.toList());
        
        assertEquals(employees.size(), empIds.length);
    }

    @Test
    public void whenFilterEmployees_thenGetFilteredStream() {
        Integer[] empIds = { 1, 2, 3, 4 };
        
        List<Employee> employees = Stream.of(empIds)
          .map(employeeRepository::findById)
          .filter(e -> e != null)
          .filter(e -> e.getSalary() > 200000)
          .collect(Collectors.toList());
        
        assertEquals(Arrays.asList(arrayOfEmps[2]), employees);
    }

    @Test
    public void whenFindFirst_thenGetFirstEmployeeInStream() {
        Integer[] empIds = { 1, 2, 3, 4 };
        
        Employee employee = Stream.of(empIds)
          .map(employeeRepository::findById)
          .filter(e -> e != null)
          .filter(e -> e.getSalary() > 100000)
          .findFirst()
          .orElse(null);
        
        assertEquals(employee.getSalary(), new Double(200000));
    }

    @Test
    public void whenCollectStreamToList_thenGetList() {
        List<Employee> employees = empList.stream().collect(Collectors.toList());
        
        assertEquals(empList, employees);
    }

    @Test
    public void whenStreamToArray_thenGetArray() {
        Employee[] employees = empList.stream().toArray(Employee[]::new);

        assertThat(empList.toArray(), equalTo(employees));
    }
    
    @Test
    public void whenStreamCount_thenGetElementCount() {
        Long empCount = empList.stream()
          .filter(e -> e.getSalary() > 200000)
          .count();

        assertEquals(empCount, new Long(1));
    }

    @Test
    public void whenLimitInfiniteStream_thenGetFiniteElements() {
        Stream<Integer> infiniteStream = Stream.iterate(2, i -> i * 2);
    
        List<Integer> collect = infiniteStream
          .skip(3)
          .limit(5)
          .collect(Collectors.toList());
  
        assertEquals(collect, Arrays.asList(16, 32, 64, 128, 256));
    }
    
    @Test
    public void whenSortStream_thenGetSortedStream() {
        List<Employee> employees = empList.stream()
          .sorted((e1, e2) -> e1.getName().compareTo(e2.getName()))
          .collect(Collectors.toList());

        assertEquals(employees.get(0).getName(), "Bill Gates");
        assertEquals(employees.get(1).getName(), "Jeff Bezos");
        assertEquals(employees.get(2).getName(), "Mark Zuckerberg");
    }


    @Test
    public void whenFindMin_thenGetMinElementFromStream() {
        Employee firstEmp = empList.stream()
          .min((e1, e2) -> e1.getId() - e2.getId())
          .orElseThrow(NoSuchElementException::new);

        assertEquals(firstEmp.getId(), new Integer(1));
    }
    
    @Test
    public void whenFindMax_thenGetMaxElementFromStream() {
        Employee maxSalEmp = empList.stream()
          .max(Comparator.comparing(Employee::getSalary))
          .orElseThrow(NoSuchElementException::new);

        assertEquals(maxSalEmp.getSalary(), new Double(300000.0));
    }
    
    @Test
    public void whenApplyDistinct_thenRemoveDuplicatesFromStream() {
        List<Integer> intList = Arrays.asList(2, 5, 3, 2, 4, 3);
        List<Integer> distinctIntList = intList.stream().distinct().collect(Collectors.toList());
        
        assertEquals(distinctIntList, Arrays.asList(2, 5, 3, 4));
    }

    @Test
    public void whenApplyMatch_thenReturnBoolean() {
        List<Integer> intList = Arrays.asList(2, 4, 5, 6, 8);
        
        boolean allEven = intList.stream().allMatch(i -> i % 2 == 0);
        boolean oneEven = intList.stream().anyMatch(i -> i % 2 == 0);
        boolean noneMultipleOfThree = intList.stream().noneMatch(i -> i % 3 == 0);
        
        assertEquals(allEven, false);
        assertEquals(oneEven, true);
        assertEquals(noneMultipleOfThree, false);
    }
    
    @Test
    public void whenFindMaxOnIntStream_thenGetMaxInteger() {
        Integer latestEmpId = empList.stream()
          .mapToInt(Employee::getId)
          .max()
          .orElseThrow(NoSuchElementException::new);

        assertEquals(latestEmpId, new Integer(3));
    }

    @Test
    public void whenApplySumOnIntStream_thenGetSum() {
        Double avgSal = empList.stream()
          .mapToDouble(Employee::getSalary)
          .average()
          .orElseThrow(NoSuchElementException::new);
        
        assertEquals(avgSal, new Double(200000));
    }
    
    @Test
    public void whenApplyReduceOnStream_thenGetValue() {
        Double sumSal = empList.stream()
          .map(Employee::getSalary)
          .reduce(0.0, Double::sum);

        assertEquals(sumSal, new Double(600000));
    }
    
    @Test
    public void whenCollectByJoining_thenGetJoinedString() {
        String empNames = empList.stream()
          .map(Employee::getName)
          .collect(Collectors.joining(", "))
          .toString();
        
        assertEquals(empNames, "Jeff Bezos, Bill Gates, Mark Zuckerberg");
    }
    
    @Test
    public void whenCollectBySet_thenGetSet() {
        Set<String> empNames = empList.stream()
                .map(Employee::getName)
                .collect(Collectors.toSet());
        
        assertEquals(empNames.size(), 3);
    }
    
    @Test
    public void whenToVectorCollection_thenGetVector() {
        Vector<String> empNames = empList.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(Vector::new));
        
        assertEquals(empNames.size(), 3);
    }

    @Test
    public void whenStreamPartition_thenGetMap() {
        List<Integer> intList = Arrays.asList(2, 4, 5, 6, 8);
        Map<Boolean, List<Integer>> isEven = intList.stream().collect(
          Collectors.partitioningBy(i -> i % 2 == 0));
        
        assertEquals(isEven.get(true).size(), 4);
        assertEquals(isEven.get(false).size(), 1);
    }
    
    @Test
    public void whenStreamGroupingBy_thenGetMap() {
        Map<Character, List<Employee>> groupByAlphabet = empList.stream().collect(
          Collectors.groupingBy(e -> new Character(e.getName().charAt(0))));

        assertEquals(groupByAlphabet.get('B').get(0).getName(), "Bill Gates");
        assertEquals(groupByAlphabet.get('J').get(0).getName(), "Jeff Bezos");
        assertEquals(groupByAlphabet.get('M').get(0).getName(), "Mark Zuckerberg");
    }

    @Test
    public void whenParallelStream_thenPerformOperationsInParallel() {
        Employee[] arrayOfEmps = {
          new Employee(1, "Jeff Bezos", 100000.0), 
          new Employee(2, "Bill Gates", 200000.0), 
          new Employee(3, "Mark Zuckerberg", 300000.0)
        };

        List<Employee> empList = Arrays.asList(arrayOfEmps);
        
        empList.stream().parallel().forEach(e -> e.salaryIncrement(10.0));
        
        assertThat(empList, contains(
          hasProperty("salary", equalTo(110000.0)),
          hasProperty("salary", equalTo(220000.0)),
          hasProperty("salary", equalTo(330000.0))
        ));
    }

    @Test
    public void whenGenerateStream_thenGetInfiniteStream() {
        Stream.generate(Math::random)
          .limit(5)
          .forEach(System.out::println);
    }

    @Test
    public void whenIterateStream_thenGetInfiniteStream() {
        Stream<Integer> evenNumStream = Stream.iterate(2, i -> i * 2);
    
        List<Integer> collect = evenNumStream
          .limit(5)
          .collect(Collectors.toList());
  
        assertEquals(collect, Arrays.asList(2, 4, 8, 16, 32));
    }

}
