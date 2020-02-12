package com.example.demo.serial;

public interface ISerializer {

    /**
     * 序列化
     * @param obj
     * @param <T>
     * @return
     */
    <T> byte[] serializer(T obj);


    /**
     * 反序列化
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T deSerializer(byte[] data, Class<T> clazz);
}
