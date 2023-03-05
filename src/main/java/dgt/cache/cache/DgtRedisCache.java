package dgt.cache.cache;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;

public class DgtRedisCache extends RedisCache {
  
    /**
     * The redisCache constructor is protected, we cannot call from outside. 
     * We inherited RedisCache and new it.
     * @param name
     * @param cacheWriter
     * @param cacheConfig
     */
    public DgtRedisCache(String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig) {
      super(name, cacheWriter, cacheConfig);
  }  
}
