public class Employee {
    String name;
    Float salary;
    int age;
    String city;

    // standard constructor, getters and setters
    public Employee(String name, Float salary, int age, String city) {
        this.name = name;
        this.salary = salary;
        this.age = age;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public Float getSalary() {
        return salary;
    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", age=" + age +
                ", city='" + city + '\'' +
                '}';
    }
}