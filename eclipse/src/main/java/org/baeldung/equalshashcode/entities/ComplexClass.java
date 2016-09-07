package org.baeldung.equalshashcode.entities;

import java.util.ArrayList;
import java.util.HashSet;

public class ComplexClass {

	private ArrayList<?> genericArrayList;
	private HashSet<Integer> integerHashSet;

	public ComplexClass(ArrayList<?> genericArrayList,
			HashSet<Integer> integerHashSet) {
		super();
		this.genericArrayList = genericArrayList;
		this.integerHashSet = integerHashSet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((genericArrayList == null) ? 0 : genericArrayList.hashCode());
		result = prime * result
				+ ((integerHashSet == null) ? 0 : integerHashSet.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ComplexClass))
			return false;
		ComplexClass other = (ComplexClass) obj;
		if (genericArrayList == null) {
			if (other.genericArrayList != null)
				return false;
		} else if (!genericArrayList.equals(other.genericArrayList))
			return false;
		if (integerHashSet == null) {
			if (other.integerHashSet != null)
				return false;
		} else if (!integerHashSet.equals(other.integerHashSet))
			return false;
		return true;
	}

	protected ArrayList<?> getGenericArrayList() {
		return genericArrayList;
	}

	protected void setGenericArrayList(ArrayList<?> genericArrayList) {
		this.genericArrayList = genericArrayList;
	}

	protected HashSet<Integer> getIntegerHashSet() {
		return integerHashSet;
	}

	protected void setIntegerHashSet(HashSet<Integer> integerHashSet) {
		this.integerHashSet = integerHashSet;
	}
}
