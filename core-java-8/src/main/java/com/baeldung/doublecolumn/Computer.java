package com.baeldung.doublecolumn;

public class Computer {

    private Integer age;
    private String color;
    private Integer healty;

    public Computer(int age, String color) {
        this.age = age;
        this.color = color;
    }

    public Computer(Integer age, String color, Integer healty) {
        this.age = age;
        this.color = color;
        this.healty = healty;
    }

    public Computer() {
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getHealty() {
        return healty;
    }

    public void setHealty(Integer healty) {
        this.healty = healty;
    }

    @Override
    public String toString() {
        return "Computer{" + "age=" + age + ", color='" + color + '\'' + ", healty=" + healty + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Computer computer = (Computer) o;

        if (age != null ? !age.equals(computer.age) : computer.age != null)
            return false;
        return color != null ? color.equals(computer.color) : computer.color == null;

    }

    @Override
    public int hashCode() {
        int result = age != null ? age.hashCode() : 0;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }
}
