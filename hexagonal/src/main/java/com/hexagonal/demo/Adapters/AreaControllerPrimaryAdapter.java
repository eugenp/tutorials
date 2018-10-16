package com.hexagonal.demo.Adapters;


import com.hexagonal.demo.service.AreaService;
import com.hexagonal.demo.domain.Area;
import com.hexagonal.demo.ports.primary.AreaPortPrimaryApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AreaControllerPrimaryAdapter implements AreaPortPrimaryApi {

    private AreaService areaService;

    @Autowired
    public AreaControllerPrimaryAdapter(AreaService areaService){
        this.areaService = areaService;

    }

    @Override
    public Area getArea(@PathVariable String zipCode) {
        return areaService.getArea(zipCode);
    }
}
