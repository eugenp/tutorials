package com.Baeldung.output.impl;
 
import com.Baeldung.output.IBaseService;
 
/**
 * 
 * 
 * @author mukesh
 *
 */
public class BaseServiceImpl implements IBaseService
{
	public void generateOutput(){
		System.out.println("Constructor Based Dependency Injection");
	}
}