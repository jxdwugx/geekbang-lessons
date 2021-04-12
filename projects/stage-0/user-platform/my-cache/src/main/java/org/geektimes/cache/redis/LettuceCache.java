package org.geektimes.cache.redis;

import io.lettuce.core.api.sync.RedisCommands;
import org.geektimes.cache.AbstractCache;
import org.geektimes.cache.ExpirableEntry;
import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import java.io.Serializable;
import java.util.Set;

/**
 * @description
 * @autor 吴光熙
 * @date 2021/4/12  19:35
 **/
public class LettuceCache<K extends Serializable, V extends Serializable> extends AbstractCache<K, V> {

    private final RedisCommands<K, V> commands;

    protected LettuceCache(CacheManager cacheManager, String cacheName, Configuration<K, V> configuration,
                           RedisCommands<K, V> commands) {
        super(cacheManager, cacheName, configuration);
        this.commands = commands;
    }

    @Override
    protected boolean containsEntry(K key) throws CacheException, ClassCastException {
        V val = commands.get(key);
        return val != null;
    }

    @Override
    protected ExpirableEntry<K, V> getEntry(K key) throws CacheException, ClassCastException {
        V val = commands.get(key);
        return ExpirableEntry.of(key, val);
    }

    @Override
    protected void putEntry(ExpirableEntry<K, V> entry) throws CacheException, ClassCastException {
        commands.set(entry.getKey(), entry.getValue());
    }

    @Override
    protected ExpirableEntry<K, V> removeEntry(K key) throws CacheException, ClassCastException {
        return null;
    }

    @Override
    protected void clearEntries() throws CacheException {

    }

    @Override
    protected Set<K> keySet() {
        return null;
    }
}
