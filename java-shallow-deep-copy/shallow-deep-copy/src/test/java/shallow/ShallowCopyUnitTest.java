package shallow;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.shallow.Company;
import com.baeldung.shallow.Profession;

public class ShallowCopyUnitTest {

    @Test
    public void whenTwoObjectsAreTheSame_thenShallowCopySuccess() throws CloneNotSupportedException {

        Profession professionA = new Profession("ProductSales", 60000.0,
            new Company("SpeedNet", "Internet", 2000));
        Profession professionB =  (Profession) professionA.clone();

        Assert.assertEquals(professionA.getJobTitle(), professionB.getJobTitle());
        Assert.assertTrue(professionA.getSalary()==professionB.getSalary());
        Assert.assertEquals(professionA.getCompany().getCompanyName(),
          professionB.getCompany().getCompanyName());
        Assert.assertEquals(professionA.getCompany().getIndustry(),
          professionB.getCompany().getIndustry());
        Assert.assertTrue(professionA.getCompany().getNumberOfEmployees()
          ==professionB.getCompany().getNumberOfEmployees());

        professionB.getCompany().setNumberOfEmployees(100);
        professionB.getCompany().setCompanyName("Marslink");
        professionB.getCompany().setIndustry("Space Exploration");

        Assert.assertEquals(professionA.getCompany().getCompanyName(),professionB.getCompany().getCompanyName());
        Assert.assertEquals(professionA.getCompany().getIndustry(),professionB.getCompany().getIndustry());
        Assert.assertTrue(professionA.getCompany().getNumberOfEmployees()==
            professionB.getCompany().getNumberOfEmployees());
    }

}
