package com.tokoin.otp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class OtpSMSCacheRepositoryImpl implements OtpSMSCacheRepository {

//    private RedisTemplate<String, String>  redisTemplate;

    private StringRedisTemplate redisTemplate;

//   @Autowired
//    private Jedis jedis;

    private static final String KEY = "Mobile";

//    private HashOperations hashOperations;

    ValueOperations<String, String> valueOps;


    //    private Jedis jedis;
//
    @Autowired
    public OtpSMSCacheRepositoryImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
//        hashOperations = redisTemplate.opsForHash();
        valueOps = redisTemplate.opsForValue();
    }

    @Override
    public void put(String key, Integer value) {
//        hashOperations.put(key, KEY, value);
        valueOps.set(key, String.valueOf(value));
//        jedis.set(key, String.valueOf(value));
//        jedis.expire(key, 20);
         redisTemplate.expire(key, 300, TimeUnit.SECONDS);
    }

    @Override
    public Optional<String> get(String key) {
//        return (Integer) hashOperations.get(key, KEY);

        Boolean b = redisTemplate.hasKey(key);
        if (Boolean.TRUE.equals(b)) {
            return Optional.ofNullable(valueOps.get(key));
        } else {
            return Optional.empty();
        }

//        return Integer.valueOf(jedis.get(key));
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }

}
