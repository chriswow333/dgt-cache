package dgt.cache.cache;

import java.util.Objects;
import java.util.concurrent.Callable;

import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DgtCache extends AbstractValueAdaptingCache  {

  /**
   * Cache name
   */
  private String name;
  
  /**
   * Use cafffeine or not
   */
  private boolean useCaffeineCache;
  
  /**
   * Redis Cacge
   */
  private RedisCache redisCache;

  /**
   * Caffeine Cache
   */
  private CaffeineCache caffeineCache;
  

  public DgtCache(boolean allowNullValues, String name, boolean useCaffeineCache, RedisCache redisCache, CaffeineCache caffeineCache) {
    super(allowNullValues);
    
    this.name = name;
    this.useCaffeineCache = useCaffeineCache;
    this.redisCache = redisCache;
    this.caffeineCache = caffeineCache;
  }


  @Override
  public String getName() {
    return name;
  }

  @Override
  public Object getNativeCache() {
    return null;
  }

  @Override
  public <T> T get(Object key, Callable<T> valueLoader) {
    Object value = lookup(key);
    return (T)value;
  }


  /**
   *  The object value need to be implemented Serializable, otherwise it dosen't work.
   *  
   * @param key
   * @param value
   */
  @Override
  public void put(Object key, Object value) {

    log.info("set data key:{}, value:{}", key.toString(), value);
    
    redisCache.put(key, value);

    if (useCaffeineCache) {
      caffeineCache.put(key, value);

      // TODO we can reset caffeine caches to other services.(use redis pub/sub)

    }
  }

  /**
   * Save and return it when key doesn't exist.
   * @param key
   * @param value
   * @return
   */
  @Override
  public ValueWrapper putIfAbsent(Object key, Object value) {


    ValueWrapper valueWrapper = redisCache.putIfAbsent(key, value);

    // caffeine cache
    if (useCaffeineCache) {
      caffeineCache.put(key, value);
    }

    return valueWrapper;
  }


  /**
   * Remove Key in Redis first and then remove it in caffeine cache.
   */
  @Override
  public void evict(Object key) {
      redisCache.evict(key);
      if (useCaffeineCache) {
        caffeineCache.put(key, null);
      }
    
  }


  @Override
  public boolean evictIfPresent(Object key) {
    return false;
  }

  @Override
  public void clear() {
    redisCache.clear();
    if (useCaffeineCache) {
      caffeineCache.clear();
    }
  }

  @Override
  protected Object lookup(Object key) {

    Assert.notNull(key, "key cannot be empty");

    ValueWrapper value;
    
    if (useCaffeineCache) {
      // get date from caffeine first.
        value = caffeineCache.get(key);
        if (Objects.nonNull(value)) {
            log.debug("lookup caffeine cache, name:{}, key:{}, value:{}", name, key, value.get());
            return value.get();
        }
      }

      value = redisCache.get(key);

      if (Objects.nonNull(value)) {

        log.debug("lookup redis cache, name:{}, key:{}, value:{}", name, key, value.get());
          
        if (useCaffeineCache) {
          log.debug("set data to caffeine cache, name:{}, key:{}, value:{}", name, key, value.get());
          ValueWrapper finalValue = value;
          caffeineCache.put(key, finalValue.get());
        }
        return value.get();
      }

      return null;
  }
  
}
