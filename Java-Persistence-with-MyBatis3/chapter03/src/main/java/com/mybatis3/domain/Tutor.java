package com.mybatis3.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author Siva
 *
 */
public class Tutor implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Integer tutorId;
	private String name;
	private String email;
	private Address address;
	private List<Course> courses;
	
	@Override
	public String toString() {
		return "Tutor [tutorId=" + tutorId + ", name=" + name + ", email=" + email
				+ ", address=" + address + ", courses=" + courses + "]";
	}
	public Tutor()
	{
	}
	public Tutor(Integer id)
	{
		this.tutorId = id;
	}
	public Integer getTutorId()
	{
		return tutorId;
	}
	public void setTutorId(Integer id)
	{
		this.tutorId = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public Address getAddress()
	{
		return address;
	}
	public void setAddress(Address address)
	{
		this.address = address;
	}
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	
}
