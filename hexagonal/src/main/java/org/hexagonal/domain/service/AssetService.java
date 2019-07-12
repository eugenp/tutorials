package org.hexagonal.domain.service;

import java.util.List;

import org.hexagonal.domain.model.Asset;

public interface AssetService {
	
	public List<Asset> getSoldAssets();
	
	public List<Asset> getValidAssets();
	
	public List<Asset> getAvailableAssets();
	
	public void addAsset(Asset newAsset);

}
