package com.hexagonal.demo.service;

import com.hexagonal.demo.domain.Area;
import com.hexagonal.demo.ports.secondary.AreaPortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AreaService {

    private AreaPortRepository areaPortRepository;

    @Autowired
    public AreaService(AreaPortRepository areaPortRepository){
        this.areaPortRepository = areaPortRepository;
    }

    public Area getArea(String zipCode){
        //Some validation
        return areaPortRepository.getArea(zipCode);
    }

}
