package datajpa.domain.domain6;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import datajpa.repository.BillingDetails3Repository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:exampleApplicationContext-persistence.xml"})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class MyTest3 {

    @Autowired
    private BillingDetails3Repository billingDetails3Repository;



    @Test
    public void test1() {
        CreditCard3 creditCard = new CreditCard3();
        creditCard.setOwner("CreditCard3");
        creditCard.setNumber("20");
        billingDetails3Repository.save(creditCard);
    }

    @Test
    public void test2() {
        BankAccount3 bankAccount = new BankAccount3();
        bankAccount.setOwner("bank3");
        bankAccount.setBankname("bank");
        bankAccount.setAccount("50");
        billingDetails3Repository.save(bankAccount);

    }

    //查询BILLINGDETAILS
    @Test
    public void test3() {
        List<BillingDetails3> billingDetails3List = billingDetails3Repository.findAll();
        System.out.println("----------");
        for (BillingDetails3 billingDetails3 : billingDetails3List) {
            System.out.println(billingDetails3.getOwner());
        }
        System.out.println("----------");
    }

    //查询CreditCard
    @Test
    public void test4() {
        List<CreditCard3> creditCard3s = billingDetails3Repository.findCreditCards();
        System.out.println("----------");
        for (BillingDetails3 billingDetails3 : creditCard3s) {
            System.out.println(billingDetails3.getOwner());
        }
        System.out.println("----------");
    }

    //查询BankAccount
    @Test
    public void test5() {
        List<BankAccount3> bankAccount3s = billingDetails3Repository.findBankAccounts();
        System.out.println("----------");
        for (BillingDetails3 billingDetails3 : bankAccount3s) {
            System.out.println(billingDetails3.getOwner());
        }
        System.out.println("----------");
    }

}
