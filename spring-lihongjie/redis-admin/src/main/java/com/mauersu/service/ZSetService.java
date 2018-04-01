package com.mauersu.service;

public interface ZSetService {

	void updateZSetValue(String serverName, int dbIndex, String key, double score, String member);

	void delZSetValue(String serverName, int dbIndex, String key, String member);

}
