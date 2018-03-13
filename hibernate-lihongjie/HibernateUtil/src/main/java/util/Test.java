package util;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s = new String("xyz");
		String str = "xyz";
		String s1 = "xyz";
		System.out.println(str == s1);
		System.out.println(s.toString());
		System.out.println(s == str);
		System.out.println(s == "xyz");
		System.out.println(s.equals("xyz"));
		if(s==str) {
			System.out.println("1");
		}
		if(s.equals("xyz")) {
			System.out.println("2");
		}
	}

}
