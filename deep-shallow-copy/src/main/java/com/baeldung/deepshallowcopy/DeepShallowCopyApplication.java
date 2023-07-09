package com.baeldung.deepshallowcopy;

import com.baeldung.deepshallowcopy.models.Category;
import com.baeldung.deepshallowcopy.models.Product;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DeepShallowCopyApplication {

	public static void main(String[] args) {
		Category electronics = new Category("Electronics");
		Product originalProduct = new Product("TV", electronics);

		// Shallow copy
		Product shallowCopiedProduct = originalProduct.shallowCopy();
		shallowCopiedProduct.getCategory().setName("Appliances");
		System.out.println("After shallow copy");
		System.out.println("Original product category: " + originalProduct.getCategory().getName()); // Outputs: Appliances
		System.out.println("Copied product category: " + shallowCopiedProduct.getCategory().getName()); // Outputs: Appliances

		// Reset the original product category
		originalProduct.getCategory().setName("Electronics");

		// Deep copy
		Product deepCopiedProduct = originalProduct.deepCopy();
		deepCopiedProduct.getCategory().setName("Appliances");
		System.out.println("\nAfter deep copy");
		System.out.println("Original product category: " + originalProduct.getCategory().getName()); // Outputs: Electronics
		System.out.println("Copied product category: " + deepCopiedProduct.getCategory().getName()); // Outputs: Appliances
	}

}
