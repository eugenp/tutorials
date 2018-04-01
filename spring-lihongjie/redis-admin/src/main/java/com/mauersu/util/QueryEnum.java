package com.mauersu.util;

public enum QueryEnum {

	middle("middle" , "like*"), 
	head("middle" , "head^"), 
	tail("middle" , "tail$"), 
	
	
	;
	
	private String queryKey;
	private String queryKeyCh;
	
	QueryEnum(String queryKey, String queryKeyCh) {
		this.queryKey = queryKey;
		this.queryKeyCh = queryKeyCh;
	}
	
	
	public String getQueryKey() {
		return queryKey;
	}
	public void setQueryKey(String queryKey) {
		this.queryKey = queryKey;
	}
	public String getQueryKeyCh() {
		return queryKeyCh;
	}
	public void setQueryKeyCh(String queryKeyCh) {
		this.queryKeyCh = queryKeyCh;
	}
	
}
