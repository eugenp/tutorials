package com.baeldung.pattern.hexagonal.domain.services;

import java.util.List;

import com.baeldung.pattern.hexagonal.domain.model.MobileDetails;

public interface MobileStoreService {

	List<MobileDetails> getMobiles();
	
	void addMobile(MobileDetails mobileDetails);
	
	void modifyMobileDetails(int modelNumber, MobileDetails mobileDetails);
	
	void removeMobile(int modelNumber);
}
