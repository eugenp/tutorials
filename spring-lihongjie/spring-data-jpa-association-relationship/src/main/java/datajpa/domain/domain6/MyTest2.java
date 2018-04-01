package datajpa.domain.domain6;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import datajpa.repository.BankAccount2Repository;
import datajpa.repository.CreditCard2Repository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:exampleApplicationContext-persistence.xml"})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class MyTest2 {


    @Autowired
    private CreditCard2Repository creditCardRepository;

    @Autowired
    private BankAccount2Repository bankAccountRepository;


    @Test
    public void test1() {
        CreditCard2 creditCard1 = new CreditCard2();
        creditCard1.setOwner("CreditCard2");
        creditCard1.setNumber("20");
        creditCardRepository.save(creditCard1);
    }

    @Test
    public void test2() {
        BankAccount2 bankAccount = new BankAccount2();
        bankAccount.setOwner("bank2");
        bankAccount.setBankname("bank");
        bankAccount.setAccount("50");
        bankAccountRepository.save(bankAccount);

    }

    //多态查询

    /**
     * SELECT
     *        BILLING_DETAILS_ID,`OWNER`,NUMBER, BA_BANKANME,BA_ACCOUNT
     * FROM
     * (SELECT BILLING_DETAILS_ID,`OWNER`,NUMBER, NULL AS BA_BANKANME, NULL AS BA_ACCOUNT FROM CreditCard2
     * UNION
     * SELECT BILLING_DETAILS_ID,`OWNER`,BA_BANKNAME, BA_ACCOUNT, NULL AS NUMBER FROM BankAccount2);
     */
    @Test
    public void test3() {

    }


}
