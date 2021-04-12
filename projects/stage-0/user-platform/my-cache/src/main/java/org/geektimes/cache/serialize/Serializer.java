package org.geektimes.cache.serialize;

/**
 * @description
 * @autor 吴光熙
 * @date 2021/4/12  20:28
 **/
public interface Serializer<T> {

    byte[] serialize(T t);

    T deserialize(byte[] bytes);
}
