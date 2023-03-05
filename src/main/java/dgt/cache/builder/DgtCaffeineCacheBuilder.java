package dgt.cache.builder;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import com.github.benmanes.caffeine.cache.Caffeine;

import org.springframework.cache.caffeine.CaffeineCache;

import dgt.cache.prop.DgtCacheProperty;

public class DgtCaffeineCacheBuilder {

  private DgtCaffeineCacheBuilder(){}

  /**
   * 由于Caffeine 不會再值過期後立即執行清楚，而是在寫入或讀取操作之後執行維護工作，或是在寫入讀取很少的情況下，偶爾執行清除操作。
   * 如果我們寫入或讀是頻率很高，可使用Cache.cleanUp()或者加scheduler去定期執行清除操作
   * Scheduler可以迅速刪除過期的元素，***Java 9 +***後的版本，可以通過Scheduler.systemScheduler(), 調用thread，達到定期清除的目的
   * @return
   */
  public static CaffeineCache build(String name, DgtCacheProperty dgtCacheProperty){

    CaffeineCache caffeineCache = new CaffeineCache(name, Caffeine.newBuilder()
      .initialCapacity(dgtCacheProperty.getInitCapacity())
      .maximumSize(dgtCacheProperty.getMaxCapacity())
      // set thread pool
      // .executor(cacheExecutor)
      // setting scheduler
      // .scheduler(Scheduler.systemScheduler())
      // listener(over the maximum cache)
      // .removalListener(new CaffeineCacheRemovalListener())
      .expireAfterAccess(Duration.of(dgtCacheProperty.getCaffeineExpireTime(), ChronoUnit.SECONDS))
      // turn on metrics
      .recordStats()
      .build());

    return caffeineCache;
  }
}
