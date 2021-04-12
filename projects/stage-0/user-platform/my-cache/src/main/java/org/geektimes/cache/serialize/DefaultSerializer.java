package org.geektimes.cache.serialize;

import javax.cache.CacheException;
import java.io.*;

/**
 * @description
 * @autor 吴光熙
 * @date 2021/4/12  20:32
 **/
public class DefaultSerializer implements Serializer {


    // 是否可以抽象出一套序列化和反序列化的 API
    public byte[] serialize(Object value) throws CacheException {
        byte[] bytes = null;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)
        ) {
            // Key -> byte[]
            objectOutputStream.writeObject(value);
            bytes = outputStream.toByteArray();
        } catch (IOException e) {
            throw new CacheException(e);
        }
        return bytes;
    }

    public Object deserialize(byte[] bytes) throws CacheException {
        if (bytes == null) {
            return null;
        }
        Object value = null;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)
        ) {
            // byte[] -> Value
            value = objectInputStream.readObject();
        } catch (Exception e) {
            throw new CacheException(e);
        }
        return value;
    }
}
