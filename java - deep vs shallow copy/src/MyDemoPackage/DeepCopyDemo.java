package MyDemoPackage;

public class DeepCopyDemo {

	public static void main(String[] args) {
		MyCopyClass obj = new MyCopyClass();
		System.out.println("The value of i is: " + obj.DoDeepCopy());
	}
}
