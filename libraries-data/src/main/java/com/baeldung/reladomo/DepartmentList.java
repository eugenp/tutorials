package com.baeldung.reladomo;
import com.gs.fw.finder.Operation;
import java.util.*;
public class DepartmentList extends DepartmentListAbstract
{
	public DepartmentList()
	{
		super();
	}

	public DepartmentList(int initialSize)
	{
		super(initialSize);
	}

	public DepartmentList(Collection c)
	{
		super(c);
	}

	public DepartmentList(Operation operation)
	{
		super(operation);
	}
}
