package com.om.hexArchEx;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Program")
public class Programs {

	private String name;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return "Program [name=" + name + "]";
	}
}