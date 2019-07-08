package org.hexagonal.dao;

import java.util.List;

import org.hexagonal.model.Asset;

public interface AssetDao {
	
	List<Asset> getAssetList();

}
