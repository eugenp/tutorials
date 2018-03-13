package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.lihongjie.demo.Application;
import com.rabbitmq.lihongjie.demo.model.Account;
import com.rabbitmq.lihongjie.demo.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AccountRepositoryTest {


    private Logger logger = LoggerFactory.getLogger(AccountRepositoryTest.class);

    @Autowired
    private AccountService accountService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testFindAll() throws IOException, TimeoutException {

        String QUEUE_NAME = "hello";
        List<Account> accounts = accountService.findAll();
        logger.info("account list size: " + accounts.size());

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);


        String message = objectMapper.writeValueAsString(accounts);

        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));



        channel.close();
        connection.close();
    }
}
