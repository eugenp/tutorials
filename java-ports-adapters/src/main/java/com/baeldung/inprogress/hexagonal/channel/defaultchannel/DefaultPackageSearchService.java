package com.baeldung.inprogress.hexagonal.channel.defaultchannel;

import com.baeldung.inprogress.hexagonal.core.backend.channel.IPackageSearchService;
import com.baeldung.inprogress.hexagonal.core.domain.Package;
import com.baeldung.inprogress.hexagonal.core.domain.SearchCriteria;
import com.baeldung.inprogress.hexagonal.util.PackageUtils;

public class DefaultPackageSearchService implements IPackageSearchService {
    @Override
    public Package searchPackage(SearchCriteria searchCriteria) {
        // actual backend / db request should go from here
        return PackageUtils.generateDummyPackage();
    }
}
