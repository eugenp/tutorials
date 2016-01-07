package org.baeldung.java8.entity;

public class ComparableHuman implements Comparable<ComparableHuman> {
    private String name;
    private int age;

    public ComparableHuman() {
        super();
    }

    public ComparableHuman(final String name, final int age) {
        super();

        this.name = name;
        this.age = age;
    }

    // API

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    // compare

    public static int compareByNameThenAge(final ComparableHuman lhs, final ComparableHuman rhs) {
        if (lhs.name.equals(rhs.name)) {
            return lhs.age - rhs.age;
        } else {
            return lhs.name.compareTo(rhs.name);
        }
    }

    //

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + age;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ComparableHuman other = (ComparableHuman) obj;
        if (age != other.age) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Comparable Human [name=").append(name).append(", age=").append(age).append("]");
        return builder.toString();
    }

    @Override
    public int compareTo(final ComparableHuman o) {
        // TODO Auto-generated method stub
        return age > o.age ? 1 : age < o.age ? -1 : 0;
    }

}
