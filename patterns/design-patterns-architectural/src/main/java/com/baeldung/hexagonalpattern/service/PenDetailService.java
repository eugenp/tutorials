package com.baeldung.hexagonalpattern.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import com.baeldung.hexagonalpattern.entity.Pen;
import com.baeldung.hexagonalpattern.port.PenDaoInterface;
import com.baeldung.hexagonalpattern.port.PenDetailInterface;

public class PenDetailService implements PenDetailInterface {

    @Autowired
    private PenDaoInterface penDaoInterface;

    @Override
    public List<String> getPenTypeDetails() {
        List<Pen> pens = penDaoInterface.getAllPens();
        return pens.stream()
            .map(pen -> pen.getType())
            .collect(Collectors.toList());
    }

}
