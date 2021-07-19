package com.baeldung.redis_scan.strategy;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

public interface ScanStrategy<T> {
    ScanResult<T> scan(Jedis jedis, String cursor, ScanParams scanParams);
}
