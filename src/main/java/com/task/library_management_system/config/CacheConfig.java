package com.task.library_management_system.config;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import javax.cache.Caching;
import java.util.List;


@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager ehCacheManager() {
        CacheConfiguration<String, ResponseEntity> cacheConfig = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, ResponseEntity.class, ResourcePoolsBuilder.heap(10))
                .build();

        javax.cache.CacheManager cacheManager = Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider")
                .getCacheManager();


        var cacheNames = List.of("BookCache", "PatronCache", "UserCache");
        for (var cacheName : cacheNames) {
            cacheManager.destroyCache(cacheName);
            cacheManager.createCache(cacheName, Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfig));
        }

        return new JCacheCacheManager(cacheManager);
    }
}