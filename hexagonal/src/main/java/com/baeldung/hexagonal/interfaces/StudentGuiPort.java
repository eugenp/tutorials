package com.baeldung.hexagonal.interfaces;

public interface StudentGuiPort {

	public Object addStudent(String pFirstName, String pLastName,
			String pAddress);

	public Object view(long id);
}
