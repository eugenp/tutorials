package com.baeldung.mybatisplus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.mybatisplus.entity.Client;
import com.baeldung.mybatisplus.mapper.ClientMapper;
import com.baeldung.mybatisplus.service.ClientService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper, Client> implements ClientService {

    @Autowired
    private ClientMapper clientMapper;

}