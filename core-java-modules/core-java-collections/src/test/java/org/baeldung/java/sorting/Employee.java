package org.baeldung.java.sorting;

public class Employee implements Comparable {

    private String name;
    private int age;
    private double salary;

    public Employee(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Employee) obj).getName()
            .equals(getName());
    }

    @Override
    public int compareTo(Object o) {
        Employee e = (Employee) o;
        return getName().compareTo(e.getName());
    }

    @Override
    public String toString() {
        return new StringBuffer().append("(")
            .append(getName())
            .append(getAge())
            .append(",")
            .append(getSalary())
            .append(")")
            .toString();
    }

}
