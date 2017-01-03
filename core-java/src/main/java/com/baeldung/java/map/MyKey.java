package com.baeldung.java.map;

public class MyKey {
    private String name;
    private int id;

    public MyKey(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        System.out.println("Calling hashCode()");
        return id;
    }

    @Override
    public String toString() {
        return "MyKey [name=" + name + ", id=" + id + "]";
    }

    @Override
    public boolean equals(Object obj) {
        System.out.println("Calling equals() for key: " + obj);
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MyKey other = (MyKey) obj;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}
