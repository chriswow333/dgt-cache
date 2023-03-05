package dgt.cache.builder;

import java.util.List;

import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleCacheManager;

import dgt.cache.cache.DgtCache;

public class DgtCacheManagerBuilder {
  
  private DgtCacheManagerBuilder(){}


  public static CacheManager build(List<DgtCache> dgtCaches){
    SimpleCacheManager manager = new SimpleCacheManager();
    manager.setCaches(dgtCaches);
    manager.afterPropertiesSet();
    return manager;
  }


  
}
