package com.baeldung.hexagonalpattern.service;

import java.util.List;
import com.baeldung.hexagonalpattern.entity.Pen;
import com.baeldung.hexagonalpattern.port.PenDaoInterface;

public class PenDaoService {

    private PenDaoInterface productDao;

    public List<Pen> getAllPens() {
        return productDao.getAllPens();
    }

}
