package jUnitScopes;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")

public class Student 
{	
	
	private String gpa;

	public String getGpa()
	{
	    return gpa;
	}
	
	public void setGpa(String gpa)
	{
	    this.gpa = gpa;
	}
}
