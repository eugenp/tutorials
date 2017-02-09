package com.baeldung.beaninjection;

import java.util.List;

public interface IExampleDAO {

    List<SampleDomainObject> getDomainList();

    SampleDomainObject createNewDomain(SampleDomainObject domainObject);

    SampleDomainObject getSomeDomain();
}
