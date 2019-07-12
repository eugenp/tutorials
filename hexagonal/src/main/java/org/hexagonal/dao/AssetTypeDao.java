package org.hexagonal.dao;

import java.util.List;

import org.hexagonal.domain.model.AssetType;

public interface AssetTypeDao {
	
	List<AssetType> getAssetTypeList();
	
	void addAsset(AssetType newAssetType);

}
