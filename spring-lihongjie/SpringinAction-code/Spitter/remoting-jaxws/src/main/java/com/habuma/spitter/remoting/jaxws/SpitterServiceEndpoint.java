package com.habuma.spitter.remoting.jaxws;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.habuma.spitter.domain.Spitter;
import com.habuma.spitter.domain.Spittle;
import com.habuma.spitter.service.SpitterService;

@WebService(serviceName="SpitterService")
public class SpitterServiceEndpoint 
    extends SpringBeanAutowiringSupport {

  @Autowired
  SpitterService spitterService;

  @WebMethod
  public void addSpittle(Spittle spittle) {
    spitterService.saveSpittle(spittle);
  }

  @WebMethod
  public void deleteSpittle(long spittleId) {
    spitterService.deleteSpittle(spittleId);
  }

  @WebMethod
  public List<Spittle> getRecentSpittles(int spittleCount) {
    return spitterService.getRecentSpittles(spittleCount);
  }

  @WebMethod
  public List<Spittle> getSpittlesForSpitter(Spitter spitter) {
    return spitterService.getSpittlesForSpitter(spitter);
  }
}
