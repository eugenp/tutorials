package org.hexagonal.controller;

import java.util.List;

import org.hexagonal.domain.model.Asset;
import org.hexagonal.domain.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssetController {
	
	@Autowired
	AssetService assetService;
	
	@GetMapping(value="/asset/getValidAssetList", produces="application/json")
	public @ResponseBody List<Asset> getValidAssetList(){
		return assetService.getValidAssets();
	}

	@GetMapping(value="/asset/getSoldAssetList", produces="application/json")
	public @ResponseBody List<Asset> getSoldAssetList(){
		return assetService.getSoldAssets();
	}
	
	@GetMapping(value="/asset/getAvailableAssetList", produces="application/json")
	public @ResponseBody List<Asset> getAvaiableAssetList(){
		return assetService.getAvailableAssets();
	}
}
