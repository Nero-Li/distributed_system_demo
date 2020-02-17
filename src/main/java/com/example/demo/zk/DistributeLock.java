package com.example.demo.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @ClassName DistributeLock
 * @Description TODO
 * @Author lyming
 * @Date 2020/2/12 12:24 下午
 **/
public class DistributeLock implements Lock, Watcher {

    private ZooKeeper zk = null;
    //自定义根节点
    private String ROOT_LOCK = "/locks";
    //等待前一个锁
    private String WAIT_LOCK ;
    //标识当前的锁
    private String  CURRENT_LOCK;

    public DistributeLock() {

        try {
            zk = new ZooKeeper("192.168.2.110:2181,192.168.2.111:2181,192.168.2.114:2181", 4000, this);
            //判断根节点是否存在,传false,因为不能再一次注册对这个节点的监听
            Stat stat = zk.exists(ROOT_LOCK, false);
            if (stat == null) {
                zk.create(ROOT_LOCK, "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lock() {

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public void process(WatchedEvent event) {

    }
}
