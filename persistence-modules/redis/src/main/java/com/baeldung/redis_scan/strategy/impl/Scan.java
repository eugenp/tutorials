package com.baeldung.redis_scan.strategy.impl;

import com.baeldung.redis_scan.strategy.ScanStrategy;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

public class Scan implements ScanStrategy<String> {


    public ScanResult<String> scan(Jedis jedis, String cursor, ScanParams scanParams) {
        return jedis.scan(cursor, scanParams);
    }
}
