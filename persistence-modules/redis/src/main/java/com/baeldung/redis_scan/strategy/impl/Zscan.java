package com.baeldung.redis_scan.strategy.impl;

import com.baeldung.redis_scan.strategy.ScanStrategy;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;
import redis.clients.jedis.resps.Tuple;

public class Zscan implements ScanStrategy<Tuple> {

    private String key;


    public Zscan(String key) {
        super();
        this.key = key;
    }


    @Override
    public ScanResult<Tuple> scan(Jedis jedis, String cursor, ScanParams scanParams) {
        return jedis.zscan(key, cursor, scanParams);
    }

}
