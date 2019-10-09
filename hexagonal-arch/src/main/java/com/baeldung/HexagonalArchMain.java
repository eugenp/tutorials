package com.baeldung;

import com.baeldung.domain.service.DomainService;
import com.baeldung.framework.Module;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class HexagonalArchMain {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new Module());
        DomainService domainService = injector.getInstance(DomainService.class);
        domainService.printData();
    }

}
