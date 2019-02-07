package com.baeldung.inprogress.hexagonal.core.backend.channel;

import com.baeldung.inprogress.hexagonal.core.domain.Package;
import com.baeldung.inprogress.hexagonal.core.domain.PackageSearchCriteria;


public interface IChannelPackageSearchService {
	Package searchPackage(PackageSearchCriteria packageSearchCriteria);
}
