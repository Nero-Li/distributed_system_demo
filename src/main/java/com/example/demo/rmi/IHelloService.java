package com.example.demo.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @ClassName IHelloService
 * @Description TODO
 * @Author lyming
 * @Date 2020/2/1 11:39 上午
 **/
public interface IHelloService extends Remote {

    String sayHello(String msg) throws RemoteException;
}
