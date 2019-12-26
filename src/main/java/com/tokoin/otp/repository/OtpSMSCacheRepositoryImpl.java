package com.tokoin.otp.repository;

import com.tokoin.otp.exception.OtpCacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class OtpSMSCacheRepositoryImpl implements OtpSMSCacheRepository {

    private StringRedisTemplate redisTemplate;
    private ValueOperations<String, String> valueOps;

    @Autowired
    public OtpSMSCacheRepositoryImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        valueOps = redisTemplate.opsForValue();
    }

    /**
     * Save the key value pair in cache
     * @param key cache key
     * @param value cache value
     */
    @Override
    public void put(String key, Integer value) {
        try {
            valueOps.set(key, String.valueOf(value));
            redisTemplate.expire(key, 300, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new OtpCacheException("Error while saving to cache "+ e);
        }
    }

    /**
     * Returns the cached value
     * @param key cached key
     * @return cached value
     */
    @Override
    public Optional<String> get(String key) {
        try {
            Boolean b = redisTemplate.hasKey(key);
            if (Boolean.TRUE.equals(b)) {
                return Optional.ofNullable(valueOps.get(key));
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new OtpCacheException("Error while retrieving from the cache ", e);
        }
    }

    /**
     * Remove the cached value
     * @param key cached key
     */
    @Override
    public void remove(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            throw new OtpCacheException("Error while removing from the cache ", e);
        }
    }

}
