package org.baeldung.equalshashcode.entities;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

public class ComplexClassTest {

	@Test
	public void testEqualsAndHashcodes() {
		
		ArrayList<String> strArrayList = new ArrayList<String>();
		strArrayList.add("abc");
		strArrayList.add("def");
		ComplexClass aObject = new ComplexClass(strArrayList, new HashSet<Integer>(45,67));
		ComplexClass bObject = new ComplexClass(strArrayList, new HashSet<Integer>(45,67));
		ComplexClass cObject = new ComplexClass(strArrayList, new HashSet<Integer>(45,67));
		
		ArrayList<String> strArrayListD = new ArrayList<String>();
		strArrayListD.add("lmn");
		strArrayListD.add("pqr");
		ComplexClass dObject = new ComplexClass(strArrayListD, new HashSet<Integer>(45,67));
		
		// equals()
		Assert.assertTrue(aObject.equals(aObject));
		Assert.assertTrue(aObject.equals(bObject) && bObject.equals(aObject));
		Assert.assertTrue(aObject.equals(bObject));
		Assert.assertTrue(bObject.equals(cObject));
		Assert.assertTrue(aObject.equals(cObject));
		// hashCode()
		Assert.assertTrue(aObject.hashCode() == bObject.hashCode());
		// non-equal objects are not equals() and have different hashCode()
		Assert.assertFalse(aObject.equals(dObject));
		Assert.assertFalse(aObject.hashCode() == dObject.hashCode());
	}

}
