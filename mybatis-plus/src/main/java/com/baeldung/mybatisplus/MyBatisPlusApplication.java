package com.baeldung.mybatisplus;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baeldung.mybatisplus.entity.Account;
import com.baeldung.mybatisplus.entity.Client;
import com.baeldung.mybatisplus.idgenerator.TimestampIdGenerator;
import com.baeldung.mybatisplus.service.AccountService;
import com.baeldung.mybatisplus.service.ClientService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;

@SpringBootApplication
@MapperScan("com.baeldung.mybatisplus.mapper")
public class MyBatisPlusApplication {

    private static final Logger logger = LoggerFactory.getLogger(MyBatisPlusApplication.class);

    private static ClientService clientService;
    private static AccountService accountService;
    private static TimestampIdGenerator timestampIdGenerator;

    public MyBatisPlusApplication(ClientService clientService, AccountService accountService, TimestampIdGenerator timestampIdGenerator) {
        MyBatisPlusApplication.clientService = clientService;
        MyBatisPlusApplication.accountService = accountService;
        MyBatisPlusApplication.timestampIdGenerator = timestampIdGenerator;
    }

    public static void main(String[] args) {
        SpringApplication.run(MyBatisPlusApplication.class, args);

        /* CRUD */

        // create
        Client client = new Client();
        client.setFirstName("Anshul");
        client.setLastName("Bansal");
        client.setEmail("anshul.bansal@example.com");
        clientService.save(client);

        // create in batches

        Client client2 = new Client();
        client2.setFirstName("Harry");

        Client client3 = new Client();
        client3.setFirstName("Ron");

        Client client4 = new Client();
        client4.setFirstName("Hermione");

        clientService.saveBatch(Arrays.asList(client2, client3, client4));

        // read
        logger.info("Read client: " + clientService.getById(client.getId())
            .toString());

        // read all
        List<Client> clientList = clientService.list();
        clientList.forEach(i -> logger.info(i.toString()));

        // update
        client.setEmail("anshul.bansal@baeldung.com");
        clientService.updateById(client);
        logger.info("Updated client: " + clientService.getById(client.getId())
            .toString());

        // delete
        clientService.removeById(client.getId());

        if (null == clientService.getById(client.getId())) {
            logger.info("Deleted client with id " + client.getId());
        }

        /* conditional builders */
        Map map = Map.of("id", 2L, "first_name", "Laxman");

        // query wrapper
        QueryWrapper<Client> clientQueryWrapper = new QueryWrapper<>();
        clientQueryWrapper.allEq(map);
        logger.info(clientService.getBaseMapper()
            .selectOne(clientQueryWrapper)
            .toString());

        // lambda query wrapper
        LambdaQueryWrapper<Client> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Client::getId, 2L);
        logger.info(clientService.getBaseMapper()
            .selectOne(lambdaQueryWrapper)
            .toString());

        // query wrapper through service layer
        QueryChainWrapper<Client> queryChainWrapper = clientService.query();
        queryChainWrapper.allEq(map);
        logger.info(clientService.getBaseMapper()
            .selectOne(queryChainWrapper.getWrapper())
            .toString());

        // lambda update wrapper
        LambdaUpdateWrapper<Client> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(Client::getEmail, "x@e.com");
        logger.info(String.valueOf(clientService.getBaseMapper()
            .update(lambdaUpdateWrapper)));

        lambdaUpdateWrapper.clear();
        lambdaUpdateWrapper.setSql("email = 'x@e.com' where id >2 ");
        logger.info(String.valueOf(clientService.getBaseMapper()
            .update(lambdaUpdateWrapper)));

        // streaming queries
        clientService.getBaseMapper()
            .selectList(Wrappers.emptyWrapper(), new ResultHandler<Client>() {
                int count = 0;

                @Override
                public void handleResult(ResultContext<? extends Client> resultContext) {
                    Client client = resultContext.getResultObject();
                    logger.info("Processing " + (++count) + "Record : " + client);
                }
            });

        // Snowflake UUID generator

        // create account
        Account account = new Account();
        account.setTerm(1);
        account.setPrincipal(new BigDecimal("1000"));
        account.setInterestRate(new BigDecimal("0.01"));
        account.setClientId(client2.getId());

        accountService.save(account);

        // Custom ID generator
        Account account2 = new Account();
        account2.setId(String.valueOf(timestampIdGenerator.nextId(account)));
        account2.setTerm(2);
        account2.setPrincipal(new BigDecimal("2000"));
        account2.setInterestRate(new BigDecimal("0.02"));
        account2.setClientId(client2.getId());

        accountService.save(account2);

        // logical delete

        accountService.removeById(account2);

        // read again
        logger.info(String.valueOf(accountService.getById(account2.getId())));
        logger.info(String.valueOf(accountService.getById(account.getId())));
    }

}