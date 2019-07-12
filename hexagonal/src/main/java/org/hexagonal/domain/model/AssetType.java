package org.hexagonal.domain.model;

public class AssetType {
	  
	private int typeId;
	private String assetTypeName;
	private String assetTypeDescription;
	private String unitsForMeasurement;
	
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getAssetTypeName() {
		return assetTypeName;
	}
	public void setAssetTypeName(String assetTypeName) {
		this.assetTypeName = assetTypeName;
	}
	public String getAssetTypeDescription() {
		return assetTypeDescription;
	}
	public void setAssetTypeDescription(String assetTypeDescription) {
		this.assetTypeDescription = assetTypeDescription;
	}
	public String getUnitsForMeasurement() {
		return unitsForMeasurement;
	}
	public void setUnitsForMeasurement(String unitsForMeasurement) {
		this.unitsForMeasurement = unitsForMeasurement;
	}
	
	

}
