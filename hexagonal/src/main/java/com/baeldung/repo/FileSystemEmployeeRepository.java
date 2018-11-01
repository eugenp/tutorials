package com.baeldung.repo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.baeldung.domain.EmployeeRepository;
import com.baeldung.model.Employee;

public class FileSystemEmployeeRepository implements EmployeeRepository {
	@Override
	public Employee saveEmployee(Employee emp) {

		FileOutputStream fout;
		try {
			fout = new FileOutputStream("employee.txt");
			ObjectOutputStream out = new ObjectOutputStream(fout);

			out.writeObject(emp);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return emp;
	}

	private FileSystemEmployeeRepository() {

	}

	public static FileSystemEmployeeRepository getInstance() {
		return FileSystemEmployeeRepositorySubClass.INSTANCE;
	}

	private static class FileSystemEmployeeRepositorySubClass {

		private static FileSystemEmployeeRepository INSTANCE = new FileSystemEmployeeRepository();
	}
}