package org.hexagonal.domain.model;

import java.util.Date;

public class Asset {
	
	private String assetId;
	private int assetTypeId;
	private String assetName;
	private Date dateOfPurchase;
	private Date dateOfSell;
	private String manufacturer;
	private String status;
	
	public String getAssetId() {
		return assetId;
	}
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	public int getAssetTypeId() {
		return assetTypeId;
	}
	public void setAssetTypeId(int assetTypeId) {
		this.assetTypeId = assetTypeId;
	}
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	public Date getDateOfPurchase() {
		return dateOfPurchase;
	}
	public void setDateOfPurchase(Date dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}
	public Date getDateOfSell() {
		return dateOfSell;
	}
	public void setDateOfSell(Date dateOfSell) {
		this.dateOfSell = dateOfSell;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
