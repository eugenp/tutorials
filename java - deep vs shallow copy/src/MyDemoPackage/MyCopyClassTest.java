package MyDemoPackage;

import static org.junit.Assert.*;

import org.junit.Test;

public class MyCopyClassTest {

	@Test
	public void testDoShallowCopy() {

		MyCopyClass obj = new MyCopyClass();
		int i = obj.DoShallowCopy();
		assertEquals(5, i);
	}

	@Test
	public void testDoDeepCopy() {
		MyCopyClass obj = new MyCopyClass();
		int i = obj.DoDeepCopy();
		assertEquals(10, i);
	}
}
