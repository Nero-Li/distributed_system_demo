package com.example.demo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName User
 * @Description TODO
 * @Author lyming
 * @Date 2020/1/31 11:42 下午
 **/
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1893346865281428718L;

    private String name;

    private int age;
}
