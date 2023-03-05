package dgt.cache.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
// @ConfigurationProperties(prefix="dgt.cache")
public class DgtCacheProperty {


  /**
   * This names represents name1,name2
   * we split by "," before auto configuration
   */
  private String names;

  /**
   * use caffeine cache or not
   */
  private Boolean useCaffeineCache = true;

  /**
   * Max capacity in caffeine cache level
   */
  private Integer maxCapacity = 512;

  /**
   * Initial Capacity in caffeine cache level
   */
  private Integer initCapacity = 64;


  /**
   * expire time in caffeine cache level
   */
  private Integer caffeineExpireTime = 300;

  /**
   * expire time in redis cache level
   */
  private Integer redisExpireTime = 600;


}
