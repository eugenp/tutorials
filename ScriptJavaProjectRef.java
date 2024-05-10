package com.ayor.pr;

public class ScriptJavaProjectRef {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

public static void main(String[] args) {
    Product product = new Product("Macbook Pro", 3000);
    Product copyOfProduct = product;

    product.name = "Alienware";
    System.out.println(product.name);
    System.out.println(copyOfProduct.name);
}
