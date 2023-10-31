package com.moashare.config;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.moashare.enumpackage.CacheType;

@Configuration
public class CacheConfig {
    @Bean
    public List<CaffeineCache> caffeineConfig() {
        return Arrays.stream(CacheType.values()) // 위에서 작성한 CacheType 열거형 인스턴스들을 values()로 전체 배열로 가져온 후
                     .map(cache -> new CaffeineCache(cache.getCacheName(), Caffeine.newBuilder()
                                                                                   .recordStats()
                                                                                   .expireAfterWrite(cache.getExpireAfterWrite(),
                                                                                                     TimeUnit.SECONDS)
                                                                                   .maximumSize(cache.getMaximumSize())
                                                                                   .build()
                          ) 
                     )
                     .toList();
    }

    @Bean
    public CacheManager cacheManager(List<CaffeineCache> caffeineCaches) {
        final SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caffeineCaches);
        return cacheManager;
    }    
}
