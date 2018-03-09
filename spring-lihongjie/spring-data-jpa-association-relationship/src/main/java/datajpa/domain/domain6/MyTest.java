package datajpa.domain.domain6;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import datajpa.repository.BillingDetailsRepository;
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
public class MyTest {

    @Autowired
    private BillingDetailsRepository billingDetailsRepository;

//    @Test
//    public void test1() {
//        BillingDetails billingDetails = new CreditCard();
//        billingDetails.setOwner("li");
//        billingDetailsRepository.save(billingDetails);
//
//    }

    @Test
    public void test2() {
        CreditCard creditCard = new CreditCard();
        creditCard.setOwner("creditCard1");
        creditCard.setExpMonth("5");
        creditCard.setExpYear("2017");
        creditCard.setNumber("20");
        creditCard.setType(CreditCardType.AMEX);
        billingDetailsRepository.save(creditCard);
    }

    @Test
    public void test3() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setOwner("lihongjie2");
        bankAccount.setAccount("100");
        bankAccount.setBankname("ICBC");
        bankAccount.setSwift("swift");
        billingDetailsRepository.save(bankAccount);

    }

    //查询billingDetails
    @Test
    public void test4() {
        List<BillingDetails> billingDetailsList = billingDetailsRepository.findBillingDetails();
        for (BillingDetails billingDetails : billingDetailsList) {
            System.out.println(billingDetails.toString());
        }
    }


    //查询CreditCard

    //查询BankAccount


}
