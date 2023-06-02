package com.baeldung.spring.cloud.bootstrap.svcrating.rating;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class RatingCacheRepository implements InitializingBean {

    @Autowired
    private LettuceConnectionFactory cacheConnectionFactory;

    private StringRedisTemplate redisTemplate;
    private ValueOperations<String, String> valueOps;
    private SetOperations<String, String> setOps;

    private ObjectMapper jsonMapper;

    public List<Rating> findCachedRatingsByBookId(Long bookId) {
        return setOps.members("book-" + bookId)
            .stream()
            .map(rtId -> {
                try {
                    return jsonMapper.readValue(valueOps.get(rtId), Rating.class);
                } catch (IOException ex) {
                    return null;
                }
            })
            .collect(Collectors.toList());
    }

    public Rating findCachedRatingById(Long ratingId) {

        try {
            return jsonMapper.readValue(valueOps.get("rating-" + ratingId), Rating.class);
        } catch (IOException e) {
            return null;
        }

    }

    public List<Rating> findAllCachedRatings() {
        List<Rating> ratings = null;

        ratings = redisTemplate.keys("rating*")
            .stream()
            .map(rtId -> {
                try {

                    return jsonMapper.readValue(valueOps.get(rtId), Rating.class);

                } catch (IOException e) {
                    return null;
                }
            })
            .collect(Collectors.toList());

        return ratings;
    }

    public boolean createRating(Rating persisted) {
        try {
            valueOps.set("rating-" + persisted.getId(), jsonMapper.writeValueAsString(persisted));
            setOps.add("book-" + persisted.getBookId(), "rating-" + persisted.getId());
            return true;
        } catch (JsonProcessingException ex) {
            return false;
        }
    }

    public boolean updateRating(Rating persisted) {
        try {
            valueOps.set("rating-" + persisted.getId(), jsonMapper.writeValueAsString(persisted));
            return true;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteRating(Long ratingId) {
        Rating toDel;
        try {

            toDel = jsonMapper.readValue(valueOps.get("rating-" + ratingId), Rating.class);
            setOps.remove("book-" + toDel.getBookId(), "rating-" + ratingId);
            redisTemplate.delete("rating-" + ratingId);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        this.redisTemplate = new StringRedisTemplate(cacheConnectionFactory);

        this.valueOps = redisTemplate.opsForValue();
        this.setOps = redisTemplate.opsForSet();

        jsonMapper = new ObjectMapper();
        jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}