package org.hexagonal.admin;

import java.util.List;

import org.hexagonal.domain.model.Asset;
import org.hexagonal.domain.model.AssetType;
import org.hexagonal.domain.service.AssetService;
import org.hexagonal.domain.service.AssetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
	
	@Autowired
	AssetTypeService assetTypeService;
	
	@Autowired
	AssetService assetService;
	
	@GetMapping(value="/assettype/getAssetTypeList", produces="application/json")
	public @ResponseBody List<AssetType> getAssetTypeList(){
		return assetTypeService.getAssetTypeList();
	}
	
	@PostMapping(value="/assettype/addAssetType")
	public void addAssetType(@RequestBody AssetType newAssetType) {
		assetTypeService.addAssetType(newAssetType);
	}
	
	@PostMapping(value="/asset/addAsset")
	public void addAsset(@RequestBody Asset newAsset) {
		assetService.addAsset(newAsset);
	}

}
