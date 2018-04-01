package com.mauersu.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import com.mauersu.util.RValue;
import com.mauersu.util.RedisApplication;

@Service
public class RedisDao extends RedisApplication {
	
	@Autowired
	RedisTemplateFactory redisTemplateFactory;
	
	//--- SET 
	public void addSTRING(String serverName, int dbIndex, String key,
			String value) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		redisTemplate.opsForValue().set(key, value);
	}
	
	public void addLIST(String serverName, int dbIndex, String key,
			String[] values) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		redisTemplate.opsForList().rightPushAll(key, values);
	}
	
	public void addSET(String serverName, int dbIndex, String key,
			String[] values) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		redisTemplate.opsForSet().add(key, values);
	}

	public void addZSET(String serverName, int dbIndex, String key,
			double[] scores, String[] members) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		Set<TypedTuple<Object>> zset = new HashSet<TypedTuple<Object>>();
		for(int i=0;i<members.length;i++) {
			final Object ob = members[i];
			final double sc = scores[i];
			zset.add(new TypedTuple () {
				private Object v;
				private double score;
				{
					v = ob;
					score = sc;
				}
				@Override
				public int compareTo(Object o) {
					if(o == null) return 1;
					if(o instanceof TypedTuple) {
						TypedTuple tto = (TypedTuple) o;
						return this.getScore()-tto.getScore() >= 0?1:-1;
					}
					return 1;
				}
				@Override
				public Object getValue() {
					return v;
				}
				@Override
				public Double getScore() {
					return score;
				}
			});
		}
		redisTemplate.opsForZSet().add(key, zset);
	}
	
	public void addHASH(String serverName, int dbIndex, String key,
			String[] fields, String[] values) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		Map<String, String> hashmap = new HashMap<String, String>();
		
		for(int i=0;i<fields.length;i++) {
			String field = fields[i];
			String value = values[i];
			hashmap.put(field, value);
		}
		redisTemplate.opsForHash().putAll(key, hashmap);
	}

	//--- GET 
	public Object getSTRING(String serverName, int dbIndex, String key) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		final Object value = redisTemplate.opsForValue().get(key);
		List list = new ArrayList();
		list.add(new RValue(value));
		return list;
	}
	
	public Object getLIST(String serverName, int dbIndex, String key) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		List<Object> values = redisTemplate.opsForList().range(key, 0, 1000);
		return RValue.creatListValue(values);
	}

	public Object getSET(String serverName, int dbIndex, String key) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		List<Object> values = redisTemplate.opsForSet().randomMembers(key, 1000);
		//Set<Object> values = redisTemplate.opsForSet().members(key);
		return RValue.creatSetValue(new HashSet(values));
	}

	public Object getZSET(String serverName, int dbIndex, String key) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		Set<TypedTuple<Object>> values = redisTemplate.opsForZSet().rangeWithScores(key, 0, 1000);
		return RValue.creatZSetValue(values);
	}

	public Object getHASH(String serverName, int dbIndex, String key) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		Map<Object, Object> values = redisTemplate.opsForHash().entries(key);
		return RValue.creatHashValue(values);
	}

	//--- delete
	public void delRedisKeys(String serverName, int dbIndex, String deleteKeys) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		String[] keys = deleteKeys.split(",");
		redisTemplate.delete(Arrays.asList(keys));
		return;
	}
	
	public void delRedisHashField(String serverName, int dbIndex, String key, String field) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		List<String> hashKeys = new ArrayList<String>();
		hashKeys.add(field);
		redisTemplate.opsForHash().delete(key, hashKeys.toArray());
		return;
	}

	public void updateHashField(String serverName, int dbIndex, String key, String field, String value) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		String hashKey = field;
		redisTemplate.opsForHash().put(key, hashKey, value);
		return;
	}

	public void delSetValue(String serverName, int dbIndex, String key, String value) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		redisTemplate.opsForSet().remove(key, value);
		return;
	}

	public void updateSetValue(String serverName, int dbIndex, String key, String value) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		redisTemplate.opsForSet().add(key, value);
		return;
	}
	
	public void delZSetValue(String serverName, int dbIndex, String key, String member) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		String value = member;
		redisTemplate.opsForZSet().remove(key, value);
		return;
	}

	public void updateZSetValue(String serverName, int dbIndex, String key, double score, String member) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		String value = member;
		redisTemplate.opsForZSet().add(key, value, score);
		return;
	}
	
	public void ldelListValue(String serverName, int dbIndex, String key) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		redisTemplate.opsForList().leftPop(key);
		return;
	}
	
	public void rdelListValue(String serverName, int dbIndex, String key) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		redisTemplate.opsForList().rightPop(key);
		return;
	}

	public void lupdateListValue(String serverName, int dbIndex, String key, String value) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		redisTemplate.opsForList().leftPush(key, value);
		return;
	}
	
	public void rupdateListValue(String serverName, int dbIndex, String key, String value) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		redisTemplate.opsForList().rightPush(key, value);
		return;
	}

	public void delRedisValue(String serverName, int dbIndex, String key) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		redisTemplate.opsForValue().set(key, "");
		return;
	}

	public void updateValue(String serverName, int dbIndex, String key, String value) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		redisTemplate.opsForValue().set(key, value);
		return;
	}
}
