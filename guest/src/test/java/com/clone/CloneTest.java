package com.clone;

import static org.junit.Assert.*;

import org.junit.Test;

public class CloneTest {

	@Test
	public void testIfCloningWorked() throws CloneNotSupportedException {

		CompanyAddress address = new CompanyAddress(1l,"HA Street","City","State","546474");
		Company company = new Company(1l,"CompantAddress",address);
		Company clonedCompany = null;	
		clonedCompany = (Company) company.clone();
		assertNotSame(company,clonedCompany);
				
		System.out.println(company != clonedCompany);
		System.out.println(company);
		System.out.println(clonedCompany);
	}

	@Test
	public void testDefaultCloning() throws CloneNotSupportedException{

		CompanyAddress address = new CompanyAddress(1L, "HA Street", "City", "State", "546474");
		Company company = new Company(1l, "CompantAddress", address);
		Company clonedCompany = null;
		
		clonedCompany = (Company) company.clone();

		clonedCompany.getCompanyAddress().setCity("new city");	
		
		System.out.println(clonedCompany.getCompanyAddress().getCity());
		System.out.println(company.getCompanyAddress().getCity());
		assertEquals(company.getCompanyAddress().getCity(),clonedCompany.getCompanyAddress().getCity());	

	}
	
	
	
	
	/* See Commented Clone method in Company class 
	 
	@Test
	public void testDeepCloning() throws CloneNotSupportedException{

		CompanyAddress address = new CompanyAddress(1L, "HA Street", "City", "State", "546474");
		Company company = new Company(1l, "CompantAddress", address);
		Company clonedCompany = null;
		
		clonedCompany = (Company) company.clone();

		clonedCompany.getCompanyAddress().setCity("new city");	
		
		System.out.println(clonedCompany.getCompanyAddress().getCity());
		System.out.println(company.getCompanyAddress().getCity());
		assertNotSame(company.getCompanyAddress().getCity(),clonedCompany.getCompanyAddress().getCity());	

	}
	*/
}