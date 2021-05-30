package com.baeldung.pattern.hexagonal.domain.repository;

import java.util.List;

import com.baeldung.pattern.hexagonal.domain.model.MobileDetails;

public interface MobileStoreRepository {

	List<MobileDetails> getMobiles();

	void saveMobile(MobileDetails mobileDetails);

	void modifyMobileDetails(int modelNumber, MobileDetails mobileDetails);

	void removeMobile(int modelNumber);
}
