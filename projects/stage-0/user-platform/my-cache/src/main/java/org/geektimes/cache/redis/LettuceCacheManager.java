package org.geektimes.cache.redis;

import io.lettuce.core.RedisClient;
import org.geektimes.cache.AbstractCacheManager;

import javax.cache.Cache;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.util.Properties;

/**
 * @description
 * @autor 吴光熙
 * @date 2021/4/12  19:40
 **/
public class LettuceCacheManager extends AbstractCacheManager {

    private final RedisClient redisClient;

    public LettuceCacheManager(CachingProvider cachingProvider, URI uri, ClassLoader classLoader, Properties properties,
                               RedisClient redisClient) {
        super(cachingProvider, uri, classLoader, properties);
        this.redisClient = redisClient;
    }

    @Override
    protected <K, V, C extends Configuration<K, V>> Cache doCreateCache(String cacheName, C configuration) {
        return new LettuceCache(this, cacheName, configuration, redisClient.connect().sync());
    }
}
