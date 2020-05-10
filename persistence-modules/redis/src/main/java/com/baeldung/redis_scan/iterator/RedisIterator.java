package com.baeldung.redis_scan.iterator;

import com.baeldung.redis_scan.strategy.ScanStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class RedisIterator<T> implements Iterator<List<T>> {

    private static Logger log = LoggerFactory.getLogger(RedisIterator.class);
    private static final int DEFAULT_SCAN_COUNT = 10;

    private final JedisPool jedisPool;
    private ScanParams scanParams;
    private String cursor;
    private ScanStrategy<T> strategy;

    public RedisIterator(JedisPool jedisPool, int initialScanCount, String pattern, ScanStrategy<T> strategy) {
        super();
        this.jedisPool = jedisPool;
        this.scanParams = new ScanParams().match(pattern).count(initialScanCount);
        this.strategy = strategy;
    }

    public RedisIterator(JedisPool jedisPool, String pattern, ScanStrategy<T> strategy) {
        super();
        this.jedisPool = jedisPool;
        this.scanParams = new ScanParams().match(pattern).count(DEFAULT_SCAN_COUNT);
        this.strategy = strategy;
    }

    @Override
    public boolean hasNext() {
        return !"0".equals(cursor);
    }

    @Override
    public List<T> next() {
        if (cursor == null) {
            cursor = "0";
        }
        try (Jedis jedis = jedisPool.getResource()) {
            ScanResult<T> scanResult = strategy.scan(jedis, cursor, scanParams);
            cursor = scanResult.getCursor();
            return scanResult.getResult();

        } catch (Exception ex) {
            log.error("Exception caught in next()", ex);
        }
        return new LinkedList<T>();
    }

}
