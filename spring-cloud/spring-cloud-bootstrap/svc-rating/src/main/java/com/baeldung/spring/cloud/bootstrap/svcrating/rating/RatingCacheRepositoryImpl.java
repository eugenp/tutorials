package com.baeldung.spring.cloud.bootstrap.svcrating.rating;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class RatingCacheRepositoryImpl implements InitializingBean, RatingCacheRepository {

   
    @Autowired
    @Qualifier("cacheJedisConnectionFactory")
    private JedisConnectionFactory cacheConnectionFactory;
    
    private StringRedisTemplate redisTemplate;
    private ValueOperations<String,String> valueOps;
    private SetOperations<String,String> setOps;
    

    public List<Rating> findCachedRatingsByBookId(Long bookId){
        return setOps.members("book-" + bookId)
            .stream()
            .map(rtId -> Rating.fromString(valueOps.get(rtId)))
            .map(rt -> {
                rt.setFromCache(true);
                return rt;
            })
            .collect(Collectors.toList());
    }
    
    public Rating findCachedRatingById(Long ratingId) {
        return Rating.fromString(valueOps.get("rating-" + ratingId));
    }
    
    public List<Rating> findAllCachedRatings(){
        return redisTemplate.keys("rating*")
            .stream()
            .map(rtId -> Rating.fromString(valueOps.get(rtId)))
            .collect(Collectors.toList());
    }
    
    public boolean createRating(Rating persisted){
        valueOps.set("rating-"+persisted.getId(), persisted.toString());
        setOps.add("book-"+persisted.getBookId(), "rating-"+persisted.getId());
        return true;
    }
    
    public boolean updateRating(Rating persisted){
        valueOps.set("rating-"+persisted.getId(), persisted.toString());
        return true;
    }
    
    public boolean deleteRating(Long ratingId){
        Rating toDel=Rating.fromString(valueOps.get("rating-"+ratingId));
        setOps.remove("book-"+toDel.getBookId(), "rating-"+ratingId);
        redisTemplate.delete("rating-"+ratingId);
        return true;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        
        this.redisTemplate = new StringRedisTemplate(cacheConnectionFactory);
        
        this.valueOps = redisTemplate.opsForValue();
        this.setOps=redisTemplate.opsForSet();
        
    }
}
