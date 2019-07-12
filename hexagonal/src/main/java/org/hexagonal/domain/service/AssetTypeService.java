package org.hexagonal.domain.service;

import java.util.List;

import org.hexagonal.domain.model.AssetType;

public interface AssetTypeService {
	
	List<AssetType> getAssetTypeList();
	
	void addAssetType(AssetType newAssetType);

}
