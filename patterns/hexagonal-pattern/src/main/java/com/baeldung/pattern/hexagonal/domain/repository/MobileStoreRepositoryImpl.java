package com.baeldung.pattern.hexagonal.domain.repository;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.pattern.hexagonal.domain.model.MobileDetails;

public class MobileStoreRepositoryImpl implements MobileStoreRepository {
	
	/*
	 * Since this code is to learn hexagonal architecture, hence explaining behavior
	 * via static list rather than implementing actual database.
	 */
	private static List<MobileDetails> mobileList = new ArrayList<>();

	@Override
	public List<MobileDetails> getMobiles() {
		return mobileList;
	}

	@Override
	public void saveMobile(MobileDetails mobileDetails) {
		mobileList.add(mobileDetails);
	}

	@Override
	public void modifyMobileDetails(int modelNumber, MobileDetails mobileDetails) {
		int existingModelIndex = -1;
		for (MobileDetails mobile : mobileList) {
			if (mobile.getModelNumber() == modelNumber) {
				existingModelIndex = mobileList.indexOf(mobile);
				break;
			}
		}
		if (existingModelIndex != -1) {
			mobileList.set(existingModelIndex, mobileDetails);
		} else {
			mobileList.add(mobileDetails);
		}
	}

	@Override
	public void removeMobile(int modelNumber) {
		for (MobileDetails mobile : mobileList) {
			if (modelNumber == mobile.getModelNumber()) {
				mobileList.remove(mobile);
			}
		}
	}

}
