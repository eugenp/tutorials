package com.rabbitmq.lihongjie.demo.dao;


import com.rabbitmq.lihongjie.demo.model.Account;

import java.util.List;

public interface AccountDao {

    List<Account> findAll();
}
