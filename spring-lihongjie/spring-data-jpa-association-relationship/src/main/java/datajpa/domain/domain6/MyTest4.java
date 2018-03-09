package datajpa.domain.domain6;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import datajpa.repository.BillingDetails4Repository;
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
public class MyTest4 {

    @Autowired
    private BillingDetails4Repository billingDetailsRepository;



    @Test
    public void test1() {
        CreditCard4 creditCard = new CreditCard4();
        creditCard.setOwner("CreditCard4");
        creditCard.setNumber("4");
        billingDetailsRepository.save(creditCard);
    }

    @Test
    public void test2() {
        BankAccount4 bankAccount4 = new BankAccount4();
        bankAccount4.setOwner("Bank4");
        bankAccount4.setAccount("100");
        bankAccount4.setBankname("ICBC");
        billingDetailsRepository.save(bankAccount4);
    }

    //查询CREDITCARD
//    @Test
//    public void test3() {
//        List<CreditCard4> creditCard4List = billingDetailsRepository.findCreditCards();
//        System.out.println("----------");
//        for (CreditCard4 creditCard : creditCard4List) {
//            System.out.println(creditCard.toString());
//        }
//        System.out.println("----------");
//    }

    //查询BankAccount 抛错

    @Test
    public void test4() {
        List<BankAccount4> bankAccount4s = billingDetailsRepository.findBankAccounts();
        System.out.println("----------");
        for (BankAccount4 bankAccount4 : bankAccount4s) {
            System.out.println(bankAccount4.toString());
        }
        System.out.println("----------");
    }
}
