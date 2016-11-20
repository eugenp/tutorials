import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class GenericsTest {

	// testing the generic method with Integer
	@Test
	public void fromArrayToListIntTest() {
		Integer[] intArray = { 1, 2, 3, 4, 5 };
		List<Integer> list = Generics.fromArrayToList(intArray);
		Iterator<Integer> iterator;
		iterator = list.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

	// testing the generic method with String
	@Test
	public void fromArrayToListStringTest() {
		String[] stringArray = { "hello1", "hello2", "hello3", "hello4", "hello5" };
		List<String> list = Generics.fromArrayToList(stringArray);
		Iterator<String> iterator;
		iterator = list.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

	// testing the generic method with Number as upper bound with Integer
	// if we test fromArrayToListWithUpperBound with any type that doesn't
	// extend Number it will fail to compile
	@Test
	public void fromArrayToListUpperboundIntTest() {
		Integer[] intArray = { 1, 2, 3, 4, 5 };
		List<Integer> list = Generics.fromArrayToListWithUpperBound(intArray);
		Iterator<Integer> iterator;
		iterator = list.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

}
