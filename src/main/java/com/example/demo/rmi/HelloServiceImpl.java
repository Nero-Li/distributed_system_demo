package com.example.demo.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @ClassName HelloServiceImpl
 * @Description TODO
 * @Author lyming
 * @Date 2020/2/1 11:40 上午
 **/
public class HelloServiceImpl extends UnicastRemoteObject implements IHelloService {


    protected HelloServiceImpl() throws RemoteException {
    }

    @Override
    public String sayHello(String msg) throws RemoteException {
        return "Hello " + msg;
    }
}
