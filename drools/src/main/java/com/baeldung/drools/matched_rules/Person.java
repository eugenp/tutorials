package com.baeldung.drools.matched_rules;

public class Person {
    private String name;
    private int age;
    private boolean eligibleToVote;
    private boolean priorityVoter;


    // Constructors
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
        this.eligibleToVote = false;
        this.priorityVoter = false;
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

    public boolean isEligibleToVote() {
        return eligibleToVote;
    }

    public void setEligibleToVote(boolean eligibleToVote) {
        this.eligibleToVote = eligibleToVote;
    }

    public boolean isPriorityVoter() {
        return priorityVoter;
    }

    public void setPriorityVoter(boolean priorityVoter) {
        this.priorityVoter = priorityVoter;
    }
}
