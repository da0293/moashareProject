package com.moashare.enumpackage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CacheType {
    CACHE_STORE("bookmarkCacheStore", 2 * 60, 50000); // 캐시 이름, 만료 시간, 최대 횟수
    private final String cacheName;     // 캐시 이름
    private final int expireAfterWrite; // 만료시간
    private final int maximumSize;      // 최대 갯수
}
