package dgt.cache.prop;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix="dgt.cache")
public class DgtCacheProperties {
  private String projectName;
  private List<DgtCacheProperty> properties;
}
