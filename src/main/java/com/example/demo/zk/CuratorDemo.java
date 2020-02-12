package com.example.demo.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * @ClassName CuratorDemo
 * @Description TODO
 * @Author lyming
 * @Date 2020/2/12 1:21 上午
 **/
public class CuratorDemo {
    public static void main(String[] args) {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("192.168.2.110:2181,192.168.2.111:2181,192.168.2.114:2181")
                .sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace("curator")
                .build();
        curatorFramework.start();

        try {

            //增加节点
            curatorFramework.create().creatingParentContainersIfNeeded()
                    .withMode(CreateMode.PERSISTENT).forPath("/mic/node1", "1".getBytes());

            //查看节点
            Stat stat = new Stat();
            byte[] bytes = curatorFramework.getData().storingStatIn(stat).forPath("/mic/node1");
            System.out.println(new String(bytes));

            //修改节点数据
            Stat newState = curatorFramework.setData().withVersion(stat.getVersion()).forPath("/mic/node1", "xx".getBytes());
            byte[] bytes2 = curatorFramework.getData().storingStatIn(newState).forPath("/mic/node1");
            System.out.println(new String(bytes2));
            //删除节点
            curatorFramework.delete().deletingChildrenIfNeeded()
                    .withVersion(newState.getVersion()).forPath("/mic/node1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
