package dgt.cache.listener;

import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CaffeineCacheRemovalListener implements RemovalListener<Object, Object>   {
  
  @Override
  public void onRemoval(@Nullable Object k, @Nullable Object v, @NonNull RemovalCause cause) {

      log.info("[Remove Caffeine Cache] key:{} reason:{}", k, cause.name());

      if (cause == RemovalCause.SIZE) {
        // do something
      }
      if (cause == RemovalCause.EXPIRED) {
          // do something
      }
      if (cause == RemovalCause.EXPLICIT) {
          // do something
      }
      if (cause == RemovalCause.REPLACED) {
          // do something
      }
  }
}
