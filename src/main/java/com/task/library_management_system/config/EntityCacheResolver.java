package com.task.library_management_system.config;


import com.task.library_management_system.controller.CrudController;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class EntityCacheResolver extends SimpleCacheResolver {

    public EntityCacheResolver(CacheManager cacheManager) {
        super(cacheManager);
    }

    @Override
    protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
        CrudController<?, ?, ?> controller = (CrudController<?, ?, ?>) context.getTarget();
        String cacheName = controller.getEntityClass().getSimpleName() + "Cache";
        return Collections.singleton(cacheName);
    }
}
