
public class ScriptShallowCopy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

public class ShallowCopyPassingValues {
	  public static void main(String[] args) {
	    Product product = new Product("Macbook Pro", 3000);
	    Product copyOfProduct = new Product(product.getName(), product.getPrice());

	    product.setName("Alienware");
	    System.out.println(product.getName());
	    System.out.println(copyOfProduct.getName());
	  }
	}
