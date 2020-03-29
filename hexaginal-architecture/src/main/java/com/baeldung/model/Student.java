package com.baeldung.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student {

    @Override
    public String toString() {
        return "Student [studentId=" + studentId + ", name=" + name + ", age=" + age + "]";
    }

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private int studentId;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;
    
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
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
}
