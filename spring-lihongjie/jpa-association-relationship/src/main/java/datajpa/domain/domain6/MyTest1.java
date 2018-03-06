package datajpa.domain.domain6;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import datajpa.repository.BankAccount1Repository;
import datajpa.repository.CreditCard1Repository;
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
public class MyTest1 {


    @Autowired
    private CreditCard1Repository creditCardRepository;

    @Autowired
    private BankAccount1Repository bankAccountRepository;


    @Test
    public void test1() {
        CreditCard1 creditCard1 = new CreditCard1();
        creditCard1.setOwner("CreditCard1");
        creditCard1.setNumber("20");
        creditCardRepository.save(creditCard1);
    }

    @Test
    public void test2() {
        BankAccount1 bankAccount = new BankAccount1();
        bankAccount.setOwner("bank1");
        bankAccount.setBankname("bank");
        bankAccount.setAccount("50");
        bankAccountRepository.save(bankAccount);

    }


}
