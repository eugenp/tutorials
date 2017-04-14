package com.baeldung.di.constructorinjection;

/**
 * Class to represent personal details of the student
 *
 */
public class PersonalDetails {

    private String name;// name of the student
    private String id;// id of the student
    private int age;// age of the student

    public PersonalDetails(String name, String id, int age) {
        this.name = name;
        this.id = id;
        this.age = age;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuffer sBuf = new StringBuffer();
        sBuf.append(" name : ")
            .append(name)
            .append(" id :  ")
            .append(id)
            .append(" age :  ")
            .append(age);
        return sBuf.toString();
    }

}
