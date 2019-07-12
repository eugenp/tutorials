package org.hexagonal.domain.service.impl;

import java.util.List;

import org.hexagonal.dao.AssetTypeDao;
import org.hexagonal.domain.model.AssetType;
import org.hexagonal.domain.service.AssetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetTypeServiceImpl implements AssetTypeService {
	
	@Autowired
	AssetTypeDao assetTypeDao;

	@Override
	public List<AssetType> getAssetTypeList() {
		return assetTypeDao.getAssetTypeList();
	}

	@Override
	public void addAssetType(AssetType newAssetType) {
		assetTypeDao.addAsset(newAssetType);		
	}

}
