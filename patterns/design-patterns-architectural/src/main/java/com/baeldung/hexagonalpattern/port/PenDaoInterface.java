package com.baeldung.hexagonalpattern.port;

import java.util.List;

import org.springframework.stereotype.Component;

import com.baeldung.hexagonalpattern.entity.Pen;

@Component
public interface PenDaoInterface {

    List<Pen> getAllPens();

}
