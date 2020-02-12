package com.example.demo.rmi;

import com.example.demo.rmi.HelloServiceImpl;
import com.example.demo.rmi.IHelloService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * @ClassName RmiServer
 * @Description TODO
 * @Author lyming
 * @Date 2020/2/1 11:44 上午
 **/
public class RmiServer {

    public static void main(String[] args) {
        try {
            IHelloService helloService = new HelloServiceImpl();
            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://127.0.0.1/Hello",helloService);
            System.out.println("服务发布成功");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
