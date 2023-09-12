package deep;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.deep.Company;
import com.baeldung.deep.Profession;

public class DeepCopyUnitTest {

    @Test
    public void whenTwoObjectsAreDifferent_thenDeepCopySuccess() throws CloneNotSupportedException {
        Company companyXYZ = new Company("HardSun Mining", "Minerals and Mining", 5000);
        Profession professionOne = new Profession("Engineering Lead", 150000, companyXYZ);
        Profession professionTwo = (Profession) professionOne.clone();

        Assert.assertEquals(
            professionOne.getCompany().getCompanyName(), professionTwo.getCompany().getCompanyName());
        Assert.assertEquals(
            professionOne.getCompany().getIndustry(), professionTwo.getCompany().getIndustry());
        Assert.assertTrue(
            professionOne.getCompany().getNumberOfEmployees()==professionTwo.getCompany().getNumberOfEmployees());

        professionTwo.getCompany().setCompanyName("Pacific Exploration");
        professionTwo.getCompany().setIndustry("Mining and Natural Gas");
        professionTwo.getCompany().setNumberOfEmployees(10000);

        Assert.assertNotEquals(
            professionOne.getCompany().getCompanyName(), professionTwo.getCompany().getCompanyName());
        Assert.assertNotEquals(
            professionOne.getCompany().getIndustry(), professionTwo.getCompany().getIndustry());
        Assert.assertTrue(
            professionOne.getCompany().getNumberOfEmployees()!=professionTwo.getCompany().getNumberOfEmployees());


    }
}
