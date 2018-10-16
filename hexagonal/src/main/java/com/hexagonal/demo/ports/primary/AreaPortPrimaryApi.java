package com.hexagonal.demo.ports.primary;


import com.hexagonal.demo.domain.Area;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface AreaPortPrimaryApi {

    @RequestMapping(value = "/area/{zipCode}", method = RequestMethod.GET)
    Area getArea(@PathVariable String zipCode);

}
