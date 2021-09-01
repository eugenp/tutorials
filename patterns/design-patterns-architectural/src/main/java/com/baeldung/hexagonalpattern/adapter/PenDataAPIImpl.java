package com.baeldung.hexagonalpattern.adapter;

import java.util.ArrayList;
import java.util.List;
import com.baeldung.hexagonalpattern.entity.Pen;
import com.baeldung.hexagonalpattern.port.PenDaoInterface;

public class PenDataAPIImpl implements PenDaoInterface {

    @Override
    public List<Pen> getAllPens() {
        List<Pen> pens = new ArrayList<>();

        // TODO REST API Call logic here

        return pens;
    }

}
