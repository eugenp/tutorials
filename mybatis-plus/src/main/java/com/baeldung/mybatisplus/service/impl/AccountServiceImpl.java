package com.baeldung.mybatisplus.service.impl;

import com.baeldung.mybatisplus.entity.Account;
import com.baeldung.mybatisplus.mapper.AccountMapper;
import com.baeldung.mybatisplus.service.AccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

}