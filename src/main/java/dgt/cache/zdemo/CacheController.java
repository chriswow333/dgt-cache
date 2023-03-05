package dgt.cache.zdemo;

import com.github.benmanes.caffeine.cache.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {
  


  @Autowired
  private CacheUtils cacheUtils;

  @GetMapping("/put/cache")
  public void put() {

      cacheUtils.putTest("tt-key");
  //    String ds = "multiLevelCache";
  //     multilevelCache.put("test-key", ds);
  }

  @GetMapping("/get/cache")
  public String get() {
     return cacheUtils.get("tt-key");
  }
}



