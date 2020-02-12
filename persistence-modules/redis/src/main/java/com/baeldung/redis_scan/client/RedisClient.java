package com.baeldung.redis_scan.client;

import com.baeldung.redis_scan.iterator.RedisIterator;
import com.baeldung.redis_scan.strategy.ScanStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class RedisClient {
    private static Logger log = LoggerFactory.getLogger(RedisClient.class);

    private static volatile RedisClient instance = null;

    private static JedisPool jedisPool;

    public static RedisClient getInstance(String ip, final int port) {
        if (instance == null) {
            synchronized (RedisClient.class) {
                if (instance == null) {
                    instance = new RedisClient(ip, port);
                }
            }
        }
        return instance;
    }

    private RedisClient(String ip, int port) {
        try {
            if (jedisPool == null) {
                jedisPool = new JedisPool(new URI("http://" + ip + ":" + port));
            }
        } catch (URISyntaxException e) {
            log.error("Malformed server address", e);
        }
    }

    public Long lpush(final String key, final String[] strings) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lpush(key, strings);
        } catch (Exception ex) {
            log.error("Exception caught in lpush", ex);
        }
        return null;
    }

    public List<String> lrange(final String key, final long start, final long stop) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lrange(key, start, stop);
        } catch (Exception ex) {
            log.error("Exception caught in lrange", ex);
        }
        return new LinkedList<String>();
    }

    public String hmset(final String key, final Map<String, String> hash) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hmset(key, hash);
        } catch (Exception ex) {
            log.error("Exception caught in hmset", ex);
        }
        return null;
    }

    public Map<String, String> hgetAll(final String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hgetAll(key);
        } catch (Exception ex) {
            log.error("Exception caught in hgetAll", ex);
        }
        return new HashMap<String, String>();
    }

    public Long sadd(final String key, final String... members) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.sadd(key, members);
        } catch (Exception ex) {
            log.error("Exception caught in sadd", ex);
        }
        return null;
    }

    public Set<String> smembers(final String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.smembers(key);
        } catch (Exception ex) {
            log.error("Exception caught in smembers", ex);
        }
        return new HashSet<String>();
    }

    public Long zadd(final String key, final Map<String, Double> scoreMembers) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zadd(key, scoreMembers);
        } catch (Exception ex) {
            log.error("Exception caught in zadd", ex);
        }
        return 0L;
    }

    public Set<String> zrange(final String key, final long start, final long stop) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrange(key, start, stop);
        } catch (Exception ex) {
            log.error("Exception caught in zrange", ex);
        }
        return new HashSet<String>();
    }

    public String mset(final HashMap<String, String> keysValues) {
        try (Jedis jedis = jedisPool.getResource()) {
            ArrayList<String> keysValuesArrayList = new ArrayList<String>();
            keysValues.forEach((key, value) -> {
                keysValuesArrayList.add(key);
                keysValuesArrayList.add(value);
            });
            return jedis.mset((keysValuesArrayList.toArray(new String[keysValues.size()])));
        } catch (Exception ex) {
            log.error("Exception caught in mset", ex);
        }
        return null;
    }

    public Set<String> keys(final String pattern) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.keys(pattern);
        } catch (Exception ex) {
            log.error("Exception caught in keys", ex);
        }
        return new HashSet<String>();
    }

    public RedisIterator iterator(int initialScanCount, String pattern, ScanStrategy strategy) {
        return new RedisIterator(jedisPool, initialScanCount, pattern, strategy);
    }

    public void flushAll() {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.flushAll();
        } catch (Exception ex) {
            log.error("Exception caught in flushAll", ex);
        }
    }

}
