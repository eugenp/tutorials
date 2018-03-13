package com.rabbitmq.lihongjie.demo.service;


import com.rabbitmq.lihongjie.demo.dao.AccountDao;
import com.rabbitmq.lihongjie.demo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountDao accountDao;

    public List<Account> findAll() {

        return accountDao.findAll();
    }
}
