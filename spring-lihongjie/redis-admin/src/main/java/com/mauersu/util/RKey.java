package com.mauersu.util;

import org.springframework.data.redis.connection.DataType;

public class RKey implements Comparable {
	
	private String key;
	private DataType type;
	
	public RKey(String key, DataType dateType) {
		this.key = key;
		this.type = dateType;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public DataType getType() {
		return type;
	}
	public void setType(DataType type) {
		this.type = type;
	}
	
	@Override
	public int compareTo(Object o) {
		if(o == null) return 1;
		if(o instanceof RKey) {
			RKey rko = (RKey) o;
			return this.getKey().compareTo(rko.getKey());
		}
		if(o instanceof String) {
			String so = (String) o;
			return this.getKey().compareTo(so);
		}
		return 1;
	}
	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(o instanceof RKey) {
			RKey rko = (RKey) o;
			return this.getKey().equals(rko.getKey());
		}
		if(o instanceof String) {
			String so = (String) o;
			return this.getKey().equals(so);
		}
		return false;
	}
	
	public boolean contains(String str) {
		return this.getKey().contains(str);
	}
	
	public String[] split(String str) {
		return this.getKey().split(str);
	}
	public boolean startsWith(String str) {
		return this.getKey().startsWith(str);
	}
	public boolean endsWith(String str) {
		return this.getKey().endsWith(str);
	}
}
