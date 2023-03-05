package dgt.cache.autoconfigure;

import java.util.Arrays;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import multilevel.cache2.builder.DgtCacheManagerBuilder;
import multilevel.cache2.builder.DgtCaffeineCacheBuilder;
import multilevel.cache2.builder.DgtRedisCacheBuilder;
import multilevel.cache2.cache.DgtCache;
import multilevel.cache2.prop.DgtCacheProperty;

@Configuration
@EnableCaching
public class DgtCacheAutoConfiguration {
  
  @Bean
  public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

    String name = "dgt-name";

    CaffeineCache caffeineCahe = DgtCaffeineCacheBuilder.build(name, new DgtCacheProperty());
    RedisCache redisCache = DgtRedisCacheBuilder.build(name, redisConnectionFactory, new DgtCacheProperty());
    
    DgtCache dgtCache = new DgtCache(true, name, true, redisCache, caffeineCahe);

    return DgtCacheManagerBuilder.build(Arrays.asList(dgtCache));
  }

}
