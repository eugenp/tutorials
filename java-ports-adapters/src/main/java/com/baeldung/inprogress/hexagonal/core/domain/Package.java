package com.baeldung.inprogress.hexagonal.core.domain;

public class Package {
	private String packageName;
	private String packageCode;

	public Package(){

	}
	public Package(String packageName,String packageCode){
		this.packageName = packageName;
		this.packageCode = packageCode;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}
}
