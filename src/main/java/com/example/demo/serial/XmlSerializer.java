package com.example.demo.serial;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @ClassName XmlSerializer
 * @Description TODO
 * @Author lyming
 * @Date 2020/2/1 1:13 上午
 **/
public class XmlSerializer implements ISerializer {

    XStream xStream = new XStream(new DomDriver());
    @Override
    public <T> byte[] serializer(T obj) {
        return xStream.toXML(obj).getBytes();
    }

    @Override
    public <T> T deSerializer(byte[] data, Class<T> clazz) {
        return (T) xStream.fromXML(new String(data));
    }
}
