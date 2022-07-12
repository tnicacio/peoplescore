package com.tnicacio.peoplescore.config.caching;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CachingConfig {

    public static final String AFFINITY_REGION_CACHE = "affinityRegion";

    @Bean
    @Qualifier(AFFINITY_REGION_CACHE)
    public CacheManager affinityRegionCache() {
        Caffeine<Object, Object> cacheConfiguration = Caffeine.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES);
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(AFFINITY_REGION_CACHE);
        cacheManager.setCaffeine(cacheConfiguration);
        return cacheManager;
    }
}
