package dgt.cache.prop;

import java.util.List;

import lombok.Data;

@Data
public class DgtCacheProperties {
 
  /**
   * project name
   */
  private String projectName = "dgt";

  /**
   * repo name
   */
  private String repoName;


  private List<DgtCacheProperty> dgtCacheProperties;
}
