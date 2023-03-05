package dgt.cache.builder;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import multilevel.cache2.cache.DgtRedisCache;
import multilevel.cache2.prop.DgtCacheProperty;


/**
 * Redis Cache Builder
 */
public class DgtRedisCacheBuilder {
 
  private DgtRedisCacheBuilder(){}
  
  public static RedisCache build(String name, RedisConnectionFactory redisConnectionFactory, DgtCacheProperty dgtCacheProperty){

    RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
    RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
    redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.of(dgtCacheProperty.getRedisExpireTime(), ChronoUnit.SECONDS));
    redisCacheConfiguration = redisCacheConfiguration.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));
    redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    redisCacheConfiguration = redisCacheConfiguration.computePrefixWith(key -> key+":");

    RedisCache redisCache = new DgtRedisCache(name, redisCacheWriter, redisCacheConfiguration);

    return redisCache;
    
  }
}
