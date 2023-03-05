package dgt.cache.zdemo;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CacheUtils {
  // @Resource
  // private DgtCache multilevelCache;
  
  @CachePut(value= "repoName:name1", key="#key")
  public String putTest(String key) {
    log.info("info real put :{}", key);
    

    String value = key + ":value";
// multilevelCache.put(key, value);

    return value;
  }

  @Cacheable(value="repoName:name1", key="#key")
  public String get(String key) {
    // String value = multilevelCache.get(key, String.class);
    log.info("info real get : {}", key);
    return "value";
  }


  @CacheEvict(value="repoName:name1", key="#key")
  public void evict(String key) {
    // String value = multilevelCache.get(key, String.class);
    log.info("info real get : {}", key);
    // return "value";
  }
}
