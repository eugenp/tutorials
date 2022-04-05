package com.baeldung.redis_scan.strategy.impl;

import com.baeldung.redis_scan.strategy.ScanStrategy;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.Map;
import java.util.Map.Entry;

public class Hscan implements ScanStrategy<Map.Entry<String, String>> {

    private String key;

    public Hscan(String key) {
        super();
        this.key = key;
    }

    @Override
    public ScanResult<Entry<String, String>> scan(Jedis jedis, String cursor, ScanParams scanParams) {
        return jedis.hscan(key, cursor, scanParams);
    }

}
