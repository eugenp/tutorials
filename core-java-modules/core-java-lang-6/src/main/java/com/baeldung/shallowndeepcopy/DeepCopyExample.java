package com.baeldung.shallowndeepcopy;

public class DeepCopyExample {
	public static void main(String[] args) {
		AddressDeepCopy addressDeepCopy = new AddressDeepCopy("Front Street", 600012, "Kerala", "Trivendram");
		CustomerDeepCopy customerOriginal = new CustomerDeepCopy(1, addressDeepCopy, "John Doe", "Subscriber");

		CustomerDeepCopy customerCopy = null;

		try {
			customerCopy = (CustomerDeepCopy) customerOriginal.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		System.out.println(customerOriginal.addressDeepCopy.city);

		customerCopy.addressDeepCopy.city = "Kozikode";

		System.out.println(customerOriginal.addressDeepCopy.city);
		System.out.println(customerCopy.addressDeepCopy.city);
	}
}
