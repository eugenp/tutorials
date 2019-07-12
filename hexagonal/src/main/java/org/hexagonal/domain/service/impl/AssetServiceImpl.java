package org.hexagonal.domain.service.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.hexagonal.dao.AssetDao;
import org.hexagonal.domain.AssetConstant;
import org.hexagonal.domain.AssetValidator;
import org.hexagonal.domain.model.Asset;
import org.hexagonal.domain.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetServiceImpl implements AssetService{
	
	@Autowired
	private AssetDao assetDao;
	
	private List<Asset> getAssetList() {
		return assetDao.getAssetList();		
	}

	@Override
	public List<Asset> getSoldAssets() {
		List<Asset> assetList = getAssetList();
		return assetList.stream().filter(asset -> asset.getStatus().equalsIgnoreCase(AssetConstant.ASSET_STATUS_SOLD)).collect(Collectors.toList());
	}

	@Override
	public List<Asset> getValidAssets() {
		List<Asset> assetList = getAssetList();
		return assetList.stream().filter(asset -> AssetValidator.isValidAsset(asset)).collect(Collectors.toList());
	}

	@Override
	public List<Asset> getAvailableAssets() {
		List<Asset> assetList = getAssetList();
		return assetList.stream().filter(asset -> asset.getStatus().equalsIgnoreCase(AssetConstant.ASSET_STATUS_UNSOLD)).collect(Collectors.toList());
	}

	@Override
	public void addAsset(Asset newAsset) {
		assetDao.addAsset(newAsset);
	}

}
