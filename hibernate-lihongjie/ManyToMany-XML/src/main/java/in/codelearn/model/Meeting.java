package in.codelearn.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Meeting {

	private Long meetingId;
	private String subject;
	private Date meetingDate;

	private Set<Employee> employees = new HashSet<Employee>();
	
	public Meeting(String subject) {
		this.subject = subject;
		this.meetingDate = new Date();
	}
	
	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(Date meetingDate) {
		this.meetingDate = meetingDate;
	}

}
