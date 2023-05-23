package com.baeldung.redis_scan.strategy.impl;

import com.baeldung.redis_scan.strategy.ScanStrategy;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

public class Sscan implements ScanStrategy<String> {

    private String key;


    public Sscan(String key) {
        super();
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ScanResult<String> scan(Jedis jedis, String cursor, ScanParams scanParams) {
        return jedis.sscan(key, cursor, scanParams);
    }
}
