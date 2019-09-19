package com.baeldung.core.modifiers;

public class Person {
    protected String name;
    private int age;
    protected String title;
    private boolean honorable;

    public Person() {
    }

    protected Person(String title, String name, int age) {
        this.title = title;
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isHonorable() {
        return honorable;
    }

    protected void setHonorable(boolean honorable) {
        this.honorable = honorable;
    }

    protected class PrimarySkill {
        private String skillName;

        public PrimarySkill(String skill) {
            skillName = skill;
        }
    }
}
