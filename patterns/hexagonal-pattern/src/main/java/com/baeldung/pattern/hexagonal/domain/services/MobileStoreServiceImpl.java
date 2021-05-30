package com.baeldung.pattern.hexagonal.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.pattern.hexagonal.domain.model.MobileDetails;
import com.baeldung.pattern.hexagonal.domain.repository.MobileStoreRepository;

public class MobileStoreServiceImpl implements MobileStoreService{
	
	private MobileStoreRepository mobileStoreRepository;
	
	@Autowired
	public MobileStoreServiceImpl(MobileStoreRepository mobileStoreRepository) {
		this.mobileStoreRepository= mobileStoreRepository;
	}

	@Override
	public List<MobileDetails> getMobiles() {
		return mobileStoreRepository.getMobiles();
	}

	@Override
	public void addMobile(MobileDetails mobileDetails) {
		mobileStoreRepository.saveMobile(mobileDetails);
	}

	@Override
	public void modifyMobileDetails(int modelNumber, MobileDetails mobileDetails) {
		mobileStoreRepository.modifyMobileDetails(modelNumber, mobileDetails);
	}

	@Override
	public void removeMobile(int modelNumber) {
		mobileStoreRepository.removeMobile(modelNumber);
	}

}
