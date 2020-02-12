package com.example.demo.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @ClassName CuratorWatcherDemo
 * @Description TODO
 * @Author lyming
 * @Date 2020/2/12 2:02 上午
 **/
public class CuratorWatcherDemo {
    public static void main(String[] args) {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("192.168.2.110:2181,192.168.2.111:2181,192.168.2.114:2181")
                .sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace("curator")
                .build();
        curatorFramework.start();

        /**
         * 1. PathChildrenCache 监听一个节点下子节点的创新,删除,更新
         * 2. NodeCache 监听一个节点的更新和创建事件
         * 3. TreeCache 综合PathChildCache 和 NodeCache的特性
         */

        try {
            //NodeCache
//            addNodeCache(curatorFramework,"/mic");
            //PathChildrenCache
//            addPathChildCache(curatorFramework,"/mic");
            //TreeCache
            addTreeCache(curatorFramework,"/mic");
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 增加PathChildCache事件Watcher
     * @param curatorFramework
     * @param path
     */
    public static void addPathChildCache(CuratorFramework curatorFramework, String path) throws Exception {
        PathChildrenCache pathChildCache = new PathChildrenCache(curatorFramework, path,true);
        PathChildrenCacheListener pathChildrenCacheListener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                System.out.println("PathChildrenCache Receive Event: "+event.getType());

            }
        };
        pathChildCache.getListenable().addListener(pathChildrenCacheListener);
        pathChildCache.start();
    }

    /**
     * 新增nodeCache事件监听
     * @param curatorFramework
     * @param path
     * @throws Exception
     */
    public static void addNodeCache(CuratorFramework curatorFramework, String path) throws Exception {
        NodeCache nodeCache = new NodeCache(curatorFramework, path,false);
        NodeCacheListener nodeCacheListener = new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("NodeCache Receive Event: "+nodeCache.getPath()+":"+nodeCache.getCurrentData().getPath());
            }
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start();
    }

    /**
     * 新增TreeCache事件监听
     * @param curatorFramework
     * @param path
     */
    public static void addTreeCache(CuratorFramework curatorFramework, String path) throws Exception {
        TreeCache treeCache = new TreeCache(curatorFramework,path);
        TreeCacheListener treeCacheListener = new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                System.out.println("TreeCache Receive Event: "+event.getType());
            }
        };
        treeCache.getListenable().addListener(treeCacheListener);
        treeCache.start();
    }
}
