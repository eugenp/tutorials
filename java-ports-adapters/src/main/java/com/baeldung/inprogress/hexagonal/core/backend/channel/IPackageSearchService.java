package com.baeldung.inprogress.hexagonal.core.backend.channel;

import com.baeldung.inprogress.hexagonal.core.domain.Package;
import com.baeldung.inprogress.hexagonal.core.domain.SearchCriteria;

public interface IPackageSearchService {
    Package searchPackage(SearchCriteria searchCriteria);
}
