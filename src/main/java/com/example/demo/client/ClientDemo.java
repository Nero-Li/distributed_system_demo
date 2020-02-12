package com.example.demo.client;

import com.example.demo.rmi.IHelloService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * @ClassName ClientDemo
 * @Description TODO
 * @Author lyming
 * @Date 2020/2/1 11:41 上午
 **/
public class ClientDemo {

    public static void main(String[] args) {
        try {
            IHelloService helloService = (IHelloService) Naming.lookup("rmi://127.0.0.1/Hello");
            System.out.println(helloService.sayHello("lyming"));
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
