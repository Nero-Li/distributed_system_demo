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
 * @ClassName ConnectionDemo
 * @Description TODO
 * @Author lyming
 * @Date 2020/2/11 11:46 下午
 **/
public class ConnectionDemo {

    public static void main(String[] args) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            ZooKeeper zk = new ZooKeeper("192.168.2.110:2181,192.168.2.111:2181,192.168.2.114:2181", 4000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
                        //如果收到了服务端的响应时间,连接成功
                        countDownLatch.countDown();
                    }
                }
            });
            System.out.println("============="+zk.getState());
            countDownLatch.await();
            System.out.println("============="+zk.getState());

            zk.create("/zk-persis-mic3", "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            Thread.sleep(1000);
            Stat stat = new Stat();
            //获取当前节点的值
            byte[] bytes = zk.getData("/zk-persis-mic3", null, stat);
            System.out.println(new String(bytes));
            //getVersion是乐观锁的一个概念
            zk.setData("/zk-persis-mic3", "1".getBytes(), stat.getVersion());
            //获取当前节点的值
            byte[] bytesNew = zk.getData("/zk-persis-mic3", null, stat);
            System.out.println(new String(bytesNew));
            //删除节点值
            zk.delete("/zk-persis-mic3",stat.getVersion());
            zk.close();
            System.in.read();

        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
}
