package org.baeldung.java8.entity;

public class Human {
    private String name;

    public Human() {
        super();
    }

    public Human(final String name) {
        super();

        this.name = name;
    }

    // API

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    //
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        final Human other = (Human) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Human [name=").append(name).append("]");
        return builder.toString();
    }

}
