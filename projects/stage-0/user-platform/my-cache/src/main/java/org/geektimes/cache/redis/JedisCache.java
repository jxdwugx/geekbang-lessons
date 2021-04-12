package org.geektimes.cache.redis;

import org.geektimes.cache.AbstractCache;
import org.geektimes.cache.ExpirableEntry;
import org.geektimes.cache.serialize.Serializer;
import redis.clients.jedis.Jedis;

import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import java.io.*;
import java.util.Set;

public class JedisCache<K extends Serializable, V extends Serializable> extends AbstractCache<K, V> {

    private final Jedis jedis;

    private final Serializer<K> keySerializer;
    private final Serializer<V> valueSerializer;


    public JedisCache(CacheManager cacheManager, String cacheName,
                      Configuration<K, V> configuration, Jedis jedis, Serializer<K> keySerializer,
                      Serializer<V> valueSerializer) {
        super(cacheManager, cacheName, configuration);
        this.jedis = jedis;
        this.keySerializer = keySerializer;
        this.valueSerializer = valueSerializer;
    }

    @Override
    protected boolean containsEntry(K key) throws CacheException, ClassCastException {
        byte[] keyBytes = keySerializer.serialize(key);
        return jedis.exists(keyBytes);
    }

    @Override
    protected ExpirableEntry<K, V> getEntry(K key) throws CacheException, ClassCastException {
        byte[] keyBytes = keySerializer.serialize(key);
        return getEntry(keyBytes);
    }

    protected ExpirableEntry<K, V> getEntry(byte[] keyBytes) throws CacheException, ClassCastException {
        byte[] valueBytes = jedis.get(keyBytes);
        return ExpirableEntry.of(keySerializer.deserialize(keyBytes), valueSerializer.deserialize(valueBytes));
    }

    @Override
    protected void putEntry(ExpirableEntry<K, V> entry) throws CacheException, ClassCastException {
        byte[] keyBytes = keySerializer.serialize(entry.getKey());
        byte[] valueBytes = valueSerializer.serialize(entry.getValue());
        jedis.set(keyBytes, valueBytes);
    }

    @Override
    protected ExpirableEntry<K, V> removeEntry(K key) throws CacheException, ClassCastException {
        byte[] keyBytes = keySerializer.serialize(key);
        ExpirableEntry<K, V> oldEntry = getEntry(keyBytes);
        jedis.del(keyBytes);
        return oldEntry;
    }

    @Override
    protected void clearEntries() throws CacheException {
        // TODO
    }


    @Override
    protected Set<K> keySet() {
        // TODO
        return null;
    }

    @Override
    protected void doClose() {
        this.jedis.close();
    }


}