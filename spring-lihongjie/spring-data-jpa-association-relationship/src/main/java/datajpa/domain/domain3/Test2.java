package datajpa.domain.domain3;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import datajpa.repository.ClientRepository;
import datajpa.repository.DataReadAccessRepository;
import datajpa.repository.UserAccountRepository;
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
public class Test2 {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private DataReadAccessRepository dataReadAccessRepository;


    @Test
    public void test1() {
        Client client = new Client();
        clientRepository.save(client);
        DataReadAccess dataReadAccess = new DataReadAccess();

        UserAccount userAccount = userAccountRepository.findOne(0L);
        client.addUserAccont(userAccount);
//        client.getUserAccounts().add(userAccount);
        client.addDataReadAccess(dataReadAccess);

        clientRepository.save(client);
    }

    @Test
    public void test2() {
        Client client = new Client();
        clientRepository.save(client);
        DataReadAccess dataReadAccess = new DataReadAccess();
        client.addDataReadAccess(dataReadAccess);
        UserAccount userAccount = userAccountRepository.findOne(0L);
        userAccount.addClient(client);
        userAccountRepository.save(userAccount);
    }

}
