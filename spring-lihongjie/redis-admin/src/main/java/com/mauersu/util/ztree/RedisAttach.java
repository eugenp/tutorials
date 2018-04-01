package com.mauersu.util.ztree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mauersu.util.Constant;

public class RedisAttach implements Constant{
	
	private String serverName;
	private int dbIndex;
	private String seprator;
	private List<String> keyPrefixs;
	
	public RedisAttach(String serverName) {
		this.serverName = serverName;
		this.dbIndex = -1;
		this.seprator = DEFAULT_REDISKEY_SEPARATOR;
		this.keyPrefixs = new ArrayList<String>();
	}
	public RedisAttach(String serverName, int dbIndex) {
		this.serverName = serverName;
		this.dbIndex = dbIndex;
		this.seprator = DEFAULT_REDISKEY_SEPARATOR;
		this.keyPrefixs = new ArrayList<String>();
	}
	
	public RedisAttach(String serverName, int dbIndex, String[] prefixs, int i) {
		this.serverName = serverName;
		this.dbIndex = dbIndex;
		this.seprator = DEFAULT_REDISKEY_SEPARATOR;
		String[] subPrefixs =  new String[i+1];
		System.arraycopy(prefixs, 0, subPrefixs, 0, i+1);
		this.keyPrefixs = Arrays.asList(subPrefixs);
	}
	public String getSeprator() {
		return seprator;
	}
	public void setSeprator(String seprator) {
		this.seprator = seprator;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public int getDbIndex() {
		return dbIndex;
	}
	public void setDbIndex(int dbIndex) {
		this.dbIndex = dbIndex;
	}
	public List<String> getKeyPrefixs() {
		return keyPrefixs;
	}
	public void setKeyPrefixs(List<String> keyPrefixs) {
		this.keyPrefixs = keyPrefixs;
	}
}
