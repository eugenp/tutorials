package com.baeldung.dubbo.remote;

/**
 * @author aiet
 */
public class GreetingsFailoverServiceImpl implements GreetingsService {

    @Override
    public String sayHi(String name) {
        System.out.println("failover implementation");
        return "hi, failover " + name;
    }

}
