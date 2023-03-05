package dgt.cache.autoconfigure;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import dgt.cache.builder.DgtCacheManagerBuilder;
import dgt.cache.builder.DgtCaffeineCacheBuilder;
import dgt.cache.builder.DgtRedisCacheBuilder;
import dgt.cache.cache.DgtCache;
import dgt.cache.prop.DgtCacheProperties;
import dgt.cache.prop.DgtCacheProperty;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableConfigurationProperties(DgtCacheProperties.class)
@EnableCaching
@Slf4j
public class DgtCacheAutoConfiguration {
  

  @Bean
  public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory,  DgtCacheProperties dgtCacheProperties) {

    List<DgtCache> dgtCaches = new ArrayList<>();

    String projectName = dgtCacheProperties.getProjectName();

    for(DgtCacheProperty dgtCacheProperty:dgtCacheProperties.getProperties()){

      String[] names = dgtCacheProperty.getNames().split(",");

      for(int i = 0; i < names.length; i++){
        
        String name = new StringBuilder()
        .append(projectName)
        .append(":")
        .append(names[i])
        .toString();

        log.info("initial dgt cache, name: {}", name);

        CaffeineCache caffeineCahe = DgtCaffeineCacheBuilder.build(name, dgtCacheProperty);
        RedisCache redisCache = DgtRedisCacheBuilder.build(name, redisConnectionFactory, dgtCacheProperty);
 
        DgtCache dgtCache = new DgtCache(true, name, true, redisCache, caffeineCahe);

        dgtCaches.add(dgtCache);

      }
     
    }

    return DgtCacheManagerBuilder.build(dgtCaches);
  }

}
