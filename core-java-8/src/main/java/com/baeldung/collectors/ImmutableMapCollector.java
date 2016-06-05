package com.baeldung.collectors;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baeldung.Employee;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

public class ImmutableMapCollector<T extends Employee, K extends Number, A extends ImmutableMap.Builder<K, T>, R extends ImmutableMap<K, T>> implements Collector<T, ImmutableMap.Builder<K, T>, ImmutableMap<K, T>> {

    private Function<T, K> keyMapper;

    public ImmutableMapCollector() {
    }

    public void setUp(Function<T, K> keyMapper) {
        this.keyMapper = keyMapper;
    }

    @Override
    public Supplier<ImmutableMap.Builder<K, T>> supplier() {
        return ImmutableMap::builder;
    }

    @Override
    public BiConsumer<ImmutableMap.Builder<K, T>, T> accumulator() {
        return (map, value) -> map.put(keyMapper.apply(value), value);
    }

    @Override
    public BinaryOperator<ImmutableMap.Builder<K, T>> combiner() {
        return (c1, c2) -> {
            c1.putAll(c2.build());
            return c1;
        };
    }

    @Override
    public Function<ImmutableMap.Builder<K, T>, ImmutableMap<K, T>> finisher() {
        return ImmutableMap.Builder::build;
    }

    @Override
    public Set<java.util.stream.Collector.Characteristics> characteristics() {
        return EnumSet.of(Characteristics.UNORDERED);
    }

    public static void main(String[] a) {
        List<Employee> empList = new ArrayList<Employee>();
        empList.add(new Employee(1, "John", 100000, 1));
        empList.add(new Employee(2, "Joe", 200000, 2));
        empList.add(new Employee(3, "Smith", 300000, 3));
        empList.add(new Employee(4, "Jack", 900000, 1));
        empList.add(new Employee(5, "Alex", 500000, 3));
        empList.add(new Employee(6, "Justin", 800000, 2));
        empList.add(new Employee(7, "Bob", 700000, 1));

        ImmutableMapCollector<Employee, Integer, Builder<Integer, Employee>, ImmutableMap<Integer, Employee>> immutableMapCollector = new ImmutableMapCollector<Employee, Integer, ImmutableMap.Builder<Integer, Employee>, ImmutableMap<Integer, Employee>>();

        immutableMapCollector.setUp((Employee e) -> e.getEmpId());
    }

    //summingDouble(ToDoubleFunction)
    public Double summingEmployeeSalaryDouble(List<Employee> empList) {
        return empList.stream().collect(Collectors.summingDouble(e -> e.getSalary()));
    }

    //summarizingDouble(ToDoubleFunction)
    public DoubleSummaryStatistics summarizingEmployeeSalaryDouble(List<Employee> empList) {
        return empList.stream().collect(Collectors.summarizingDouble(e -> e.getSalary()));
    }

    //min(Comparator)
    public Optional<Employee> employeeWithMinSalary(List<Employee> empList) {
        Optional<Employee> min = empList.stream().collect(Collectors.minBy(new Comparator<Employee>() {
            public int compare(Employee o1, Employee o2) {
                return o1.compare(o1, o2);
            }
        }));
        return min;
    }

    //max(Comparator)
    public Optional<Employee> employeeWithMaxSalary(List<Employee> empList) {
        Optional<Employee> max = empList.stream().collect(Collectors.maxBy(new Comparator<Employee>() {
            public int compare(Employee o1, Employee o2) {
                return o1.compare(o1, o2);
            }
        }));
        return max;
    }

    //groupingBy(Function, Collector)
    public Map<Integer, Optional<Employee>> groupByReducingMax(List<Employee> empList) {
        return empList.stream().collect(Collectors.groupingBy(Employee::getDeptId, Collectors.reducing(BinaryOperator.maxBy(Comparator.comparing(Employee::getSalary)))));
    }

    //averagingDouble
    public Double averagingDouble(List<Employee> empList) {
        return empList.stream().collect(Collectors.averagingDouble(e -> e.getSalary()));
    }

    //mappingDeptEmpSal(function, collector)
    public Map<Integer, Set<Long>> mappingDeptEmpSal(List<Employee> empList) {
        return empList.stream().collect(Collectors.groupingBy(Employee::getDeptId, Collectors.mapping(Employee::getSalary, Collectors.toSet())));
    }

    //Joining(delimiter, prefix, suffix)
    public String joining() {
        return Stream.of("1", "2", "3").collect(Collectors.joining(",", "<", ">"));
    }

}
