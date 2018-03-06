package com.mybatis3.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Siva
 *
 */
public class Course implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Integer courseId;
	private String name;
	private String description;
	private Date startDate;
	private Date endDate;
	private Tutor tutor;
	private List<Student> students;
	
	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", name=" + name + ", description="
				+ description + ", startDate=" + startDate + ", endDate="
				+ endDate + ", tutor=" + tutor + ", students=" + students + "]";
	}
	public Integer getCourseId()
	{
		return courseId;
	}
	public void setCourseId(Integer id)
	{
		this.courseId = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public Date getStartDate()
	{
		return startDate;
	}
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}
	public Date getEndDate()
	{
		return endDate;
	}
	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}
	public List<Student> getStudents()
	{
		if(students == null){
			students = new ArrayList<Student>(0);
		}
		return students;
	}
	public void setStudents(List<Student> students)
	{
		this.students = students;
	}
	public Tutor getTutor() {
		return tutor;
	}
	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}
	
}
