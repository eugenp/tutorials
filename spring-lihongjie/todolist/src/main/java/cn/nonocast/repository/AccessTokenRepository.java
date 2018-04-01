package cn.nonocast.repository;

import cn.nonocast.model.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository
public class AccessTokenRepository {
	private final String prefix = "token:";

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	@Resource(name = "redisTemplate")
	private ValueOperations<String, AccessToken> vOps;

	public AccessToken get(String email) {
		return vOps.get(getKey(email));
	}

	public void save(AccessToken token) {
		vOps.set(getKey(token.getEmail()), token);
	}

	public void invalidate(AccessToken token) {
		vOps.getOperations().expire(getKey(token.getEmail()), 1, TimeUnit.DAYS);
	}

	private String getKey(String email) {
		return this.prefix.concat(email);
	}

	public List<AccessToken> findAll() {
		List<AccessToken> result = new ArrayList<>();
		Set<String> keys = vOps.getOperations().keys(prefix+"*");
		for(String key : keys) {
			result.add(vOps.get(key));
		}
		return result;
	}

	public Page<AccessToken> findAll(Pageable pageable) {
		List<AccessToken> tokens = findAll();
		return new PageImpl<>(tokens, pageable, tokens.size());
	}
}
