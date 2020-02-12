package com.example.demo.serial;

import com.alibaba.fastjson.JSON;

/**
 * @ClassName FastJsonSerializer
 * @Description TODO
 * @Author lyming
 * @Date 2020/2/1 1:28 上午
 **/
public class FastJsonSerializer implements ISerializer {
    @Override
    public <T> byte[] serializer(T obj) {
        return JSON.toJSONString(obj).getBytes();
    }

    @Override
    public <T> T deSerializer(byte[] data, Class<T> clazz) {
        return JSON.parseObject(new String(data),clazz);
    }
}
