package org.hexagonal.domain;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.hexagonal.domain.model.Asset;
import org.hexagonal.exception.BusinessException;

public class AssetValidator {
	
	public static boolean isValidAsset(Asset asset) {
		try {
			validatePurchasedDate(asset);
			validateSoldDate(asset);
			validateStatus(asset);
			return true;
		} catch (BusinessException be){
			return false;
		}	
	}
	
	private static void validatePurchasedDate(Asset asset) throws BusinessException {
		if(null != asset.getDateOfPurchase()) {
			if(asset.getDateOfPurchase().compareTo(new Date()) > 1) {
				throw new BusinessException("Date of purchase can't be future date");
			}
			if(null != asset.getDateOfSell() && asset.getDateOfPurchase().compareTo(asset.getDateOfSell()) > 1) {
					throw new BusinessException("Date of purchase is greater than date of sell");
			}
		} else {
			throw new BusinessException("Date of purchase is mandatory");
		}
	}
	
	private static void validateSoldDate(Asset asset) throws BusinessException {
		if(null != asset.getDateOfSell()) {
			if(asset.getDateOfSell().compareTo(new Date()) > 1) {
				throw new BusinessException("Date of Sell can't be future date");
			}
			if(!AssetConstant.ASSET_STATUS_SOLD.equalsIgnoreCase(asset.getStatus())) {
					throw new BusinessException("Date of sell can't be present for unsold asset");
			}
		} 
	}
	
	private static void validateStatus(Asset asset) throws BusinessException {
		if(StringUtils.isEmpty(asset.getStatus())) {
			throw new BusinessException("Asset status is mandatory");
		} else if (asset.getStatus().equalsIgnoreCase(AssetConstant.ASSET_STATUS_DEFECT) || asset.getStatus().equalsIgnoreCase(AssetConstant.ASSET_STATUS_STALED)) {
			throw new BusinessException("Asset is not for use");			
		}
	}
	

}
