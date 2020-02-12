package com.example.demo.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName WatchDemo
 * @Description TODO
 * @Author lyming
 * @Date 2020/2/12 12:18 上午
 **/
public class WatchDemo {

    public static void main(String[] args) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            ZooKeeper zk = new ZooKeeper("192.168.2.110:2181,192.168.2.111:2181,192.168.2.114:2181", 4000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("默认监听事件被调用: "+watchedEvent.getType()+" -> "+watchedEvent.getPath());
                    if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
                        //如果收到了服务端的响应时间,连接成功
                        System.out.println("连接成功前断点  event.getState="+watchedEvent.getState());
                        countDownLatch.countDown();
                    }
                }
            });
            System.out.println("=============" + zk.getState());
            countDownLatch.await();
            System.out.println("=============" + zk.getState());
            zk.create("/zk-persis-mic3", "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

            //exist  getData getChildren
            //通过exist绑定事件
            Stat stat = zk.exists("/zk-persis-mic3", new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    System.out.println("一次性监听事件被调用: "+event.getType() + "->" + event.getPath());
                    try {
                        //调用默认绑定事件
                        zk.exists("/zk-persis-mic3", true);
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            //通过修改操作来出发监听事件
            Stat newStat = zk.setData("/zk-persis-mic3", "1".getBytes(), stat.getVersion());

            zk.delete("/zk-persis-mic3",newStat.getVersion());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
}
