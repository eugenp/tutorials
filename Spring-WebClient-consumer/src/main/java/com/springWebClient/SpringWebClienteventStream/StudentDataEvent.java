package com.springWebClient.SpringWebClienteventStream;

import java.util.Date;

public class StudentDataEvent {

    private Student student;
    private Date date;

    public StudentDataEvent(Student student, Date date) {
        this.student = student;
        this.date = date;
    }

    public Student getStudent() {
		return student;
	}


	public void setStudent(Student student) {
		this.student = student;
	}


	public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
