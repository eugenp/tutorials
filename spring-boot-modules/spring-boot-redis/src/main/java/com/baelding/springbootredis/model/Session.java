package com.baelding.springbootredis.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Data
@Builder
@EqualsAndHashCode
@RedisHash(timeToLive = 60L)
public class Session {
    @Id
    private String id;
    @TimeToLive
    private Long expirationInSeconds;
}
