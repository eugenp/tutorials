package tutorial.collection.tutorial1;

import java.util.ArrayList;
import java.util.List;

public class Test1 {

	public static void main(String[] args) {
		
		List list = new ArrayList<Long>();
/*		list.add("123");
		System.out.println(list.contains("123"));
		list.add("abc");
		System.out.println(list.remove("abc"));*/
		
		A a1 = new A();
		a1.setId(1);
		A a2 = new A();
		a2.setId(1);
		
		list.add(a1);
		System.out.println(list.contains(a2));
	}
}
