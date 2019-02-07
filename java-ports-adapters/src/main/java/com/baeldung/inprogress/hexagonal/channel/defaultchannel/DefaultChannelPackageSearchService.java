package com.baeldung.inprogress.hexagonal.channel.defaultchannel;

import com.baeldung.inprogress.hexagonal.core.backend.channel.IChannelPackageSearchService;
import com.baeldung.inprogress.hexagonal.core.domain.PackageSearchCriteria;
import com.baeldung.inprogress.hexagonal.core.domain.Package;
import com.baeldung.inprogress.hexagonal.util.PackageUtils;

public class DefaultChannelPackageSearchService implements IChannelPackageSearchService {
	@Override public Package searchPackage(PackageSearchCriteria packageSearchCriteria) {
		// actual backend / db request should go from here
		return PackageUtils.generateDummyPackage();
	}
}
