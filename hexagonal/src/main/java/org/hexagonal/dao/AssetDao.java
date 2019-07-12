package org.hexagonal.dao;

import java.util.List;

import org.hexagonal.domain.model.Asset;

public interface AssetDao {
	
	List<Asset> getAssetList();
	
	void addAsset(Asset newAsset);

}
