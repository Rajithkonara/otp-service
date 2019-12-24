package com.tokoin.otp.configuration;

/**
 * Test class
 */
public class TestConfigBean {


//    public LettuceConnectionFactory lettuceConnectionFactory() {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(url, port);
//        redisStandaloneConfiguration.setPassword(password);
//
//        return new LettuceConnectionFactory(redisStandaloneConfiguration);
//    }


    // working template
//    @Bean
//    public RedisTemplate<String, String> redisTemplate() {
//        final StringRedisTemplate template = new StringRedisTemplate();
//        template.setConnectionFactory(());
//        return template;
//    }


//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory() {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(url, port);
//        redisStandaloneConfiguration.setPassword(password);
//        JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder().usePooling().build();
//        JedisConnectionFactory factory = new JedisConnectionFactory(redisStandaloneConfiguration,jedisClientConfiguration);
//        factory.afterPropertiesSet();
//        return factory;
//        //return new JedisConnectionFactory();
//    }


    /**
     * Jedis implementation
     * @return
     */
//   @Bean
//   public Jedis jedis() {
//        JedisShardInfo shardInfo = new JedisShardInfo(url, port);
//        shardInfo.setPassword(password);
//        Jedis jedis = new Jedis(shardInfo);
//        jedis.connect();
//        return jedis;
//   }



//working bean
//    @Bean
//    public RedisTemplate<String, String> redisTemplate() {
//        final RedisTemplate<String, String> template = new RedisTemplate<>();
//
//        template.setConnectionFactory(jedisConnectionFactory());
//        return template;
//    }

//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        final RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(jedisConnectionFactory());
//        return template;
//    }


//
//    @Bean
//    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
//
//        RedisCacheWriter redisCacheWriter = RedisCacheWriter.lockingRedisCacheWriter(connectionFactory);
//        RedisSerializationContext.SerializationPair<Object> valueSerializationPair = RedisSerializationContext.SerializationPair
//                .fromSerializer(new GenericJackson2JsonRedisSerializer());
//        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
//        cacheConfiguration = cacheConfiguration.serializeValuesWith(valueSerializationPair);
//        cacheConfiguration = cacheConfiguration.entryTtl(Duration.ofSeconds(30));
//
//        RedisCacheManager redisCacheManager = new RedisCacheManager(redisCacheWriter, cacheConfiguration);
//        return redisCacheManager;
//    }


//    @Bean
//    public RedisCacheConfiguration cacheConfiguration() {
//
//        log.info("Info -> Redis Cache Configuration");
//
//        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofSeconds(expireTime))
//                .disableCachingNullValues()
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
//        cacheConfig.usePrefix();
//
//        log.info("Duration -> " + cacheConfig.getTtl().getSeconds());
//
//        return cacheConfig;
//    }



}
