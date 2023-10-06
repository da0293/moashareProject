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

	// cacheManager 를 Bean 으로 등록
	// CacheType 에 등록한 캐시들을 Caffeine 캐시 객체로 생성 후 SimpleCacheManager 객체에 등록하였다.
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
    //해당 인스턴스들을 순회하면서 설정해놓은 프로퍼티(name, secsToExpireAfterWrite, maximumSize)를 이용하여 List<CaffeineCache>로 매핑한다.      		 
                     )
                     .toList();
    }

    @Bean
    public CacheManager cacheManager(List<CaffeineCache> caffeineCaches) {
        final SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caffeineCaches);
        return cacheManager;
    }
   // CacheManager는 Collection<? extends Cache>를 매개변수로 하는 set 메서드를 가지므로, 위에서 생성한 List<CaffeineCache>를 set한다.
   // 이렇게 캐시가 세팅된 CacheManager를 bean으로 등록하면, CacheType 인스턴스가 갖는 CacheName을 이용해 추상화된 in-memory 캐싱을 사용할 수 있다.
    
}
