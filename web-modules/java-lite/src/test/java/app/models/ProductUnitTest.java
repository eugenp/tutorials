package app.models;

import org.javalite.activejdbc.Base;
import org.junit.Assert;
import org.junit.Test;

public class ProductUnitTest {

    //@Test
    public void givenSavedProduct_WhenFindFirst_ThenSavedProductIsReturned() {
        //Open DB connection
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/dbname", "user", "password");

        //Create a product and save it
        Product toSaveProduct = new Product();
        toSaveProduct.set("name", "Bread");
        toSaveProduct.saveIt();

        //Find product
        Product savedProduct = Product.findFirst("name = ?", "Bread");

        Assert.assertEquals(toSaveProduct.get("name"), savedProduct.get("name"));
    }

}