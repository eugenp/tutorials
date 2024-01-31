package com.baeldung.shallowndeepcopy;

public class ShallowCopyExample {
	public static void main(String[] args) {
		AddressShallowCopy addressShallowCopy = new AddressShallowCopy("Front Street", 600012, "Kerala", "Trivendram");
		CustomerShallowCopy customerOriginal = new CustomerShallowCopy(1, addressShallowCopy, "John Doe", "Subscriber");

		CustomerShallowCopy customerCopy = null;

		try {
			customerCopy = (CustomerShallowCopy) customerOriginal.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		System.out.println(customerOriginal.addressShallowCopy.city);

		customerCopy.addressShallowCopy.city = "Kozikode";

		System.out.println(customerOriginal.addressShallowCopy.city);
		System.out.println(customerCopy.addressShallowCopy.city);
	}
}
