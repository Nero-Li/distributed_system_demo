package com.example.demo;

import com.example.demo.serial.FastJsonSerializer;
import com.example.demo.serial.ISerializer;
import com.example.demo.serial.JavaSerializer;
import com.example.demo.serial.UserProto;
import com.example.demo.serial.XmlSerializer;
import com.google.protobuf.ByteString;
import lombok.SneakyThrows;

/**
 * @ClassName App
 * @Description TODO
 * @Author lyming
 * @Date 2020/1/31 11:42 下午
 **/
public class App {
    @SneakyThrows
    public static void main(String[] args) {
//        ISerializer iSerializer = new FastJsonSerializer();
//        User user = new User();
//        user.setAge(18);
//        user.setName("lym");
//        byte[] bytes = iSerializer.serializer(user);
//        System.out.println(new String(bytes));
//
//        User user1 = iSerializer.deSerializer(bytes, User.class);
//        System.out.println(user1);



        UserProto.User user = UserProto.User.newBuilder()
                .setAge(18)
                .setName("lyming").build();
        //序列化
        ByteString bytes = user.toByteString();

        System.out.println(bytes.size());

        //反序列化
        UserProto.User nUser = UserProto.User.parseFrom(bytes);

        System.out.println(nUser);
    }
}
