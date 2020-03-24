package com.baeldung.ejb.stateless;

import javax.ejb.EJB;

public class EJBClient1 {

    @EJB
    public StatelessEJB statelessEJB;

}