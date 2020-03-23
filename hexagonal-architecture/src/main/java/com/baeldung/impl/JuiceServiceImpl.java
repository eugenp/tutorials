package com.baeldung.impl;

import com.baeldung.domain.Juice;
import com.baeldung.port.inbound.JuiceService;
import com.baeldung.port.outbound.JuiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class JuiceServiceImpl implements JuiceService {

    @Autowired
    private JuiceDao dao;

    public JuiceServiceImpl(@Autowired JuiceDao dao) {
        this.dao = dao;
    }

    @Override
    public Juice retrieveJuice(String name) {
        return dao.getJuice(name);
    }

    @Override
    public void createJuice(Juice juice) {
        dao.addJuice(juice);
    }

    @Override
    public List<Juice> listJuices() {
        return Collections.unmodifiableList(dao.getAllJuice());
    }
}
