package com.baeldung.objectcopy.objects;

public class Person implements Cloneable { 
    private String name; 
    private int score; 
    private List<String> hobbies; 
    public Person(String name, int score, List<String> hobbies) { 
        this.name = name; 
        this.score = score; 
        this.hobbies = hobbies; 
    } 
    public Person(Person other) { 
        this.name = other.name; 
        this.score = other.score; 
        this.hobbies = new ArrayList<>(other.hobbies); 
    } 
    public void setName(String name) { 
        this.name = name; 
    } 
    public String getName() { 
        return name; 
    } 
    public void setScore(int score) {
        this.score = score; 
    } 
    public int getScore() { 
        return score; 
    } 
    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies; 
    } 
    public List<String> getHobbies() {
        return hobbies; 
    } 
    @Override protected Object clone() {
        try { 
            Person cloned = (Person) super.clone(); 
            cloned.hobbies = new ArrayList<>(hobbies); 
            return cloned; 
        } 
        catch (CloneNotSupportedException e) {
            throw new AssertionError(e); 
        } 
    } 
}
