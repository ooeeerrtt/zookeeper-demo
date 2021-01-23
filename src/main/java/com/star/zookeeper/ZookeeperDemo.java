package com.star.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ZookeeperDemo {
    // zk集群地址
    private String connectString = "192.168.140.101:2181,192.168.140.102:2181,192.168.140.103:2181";
    // 超时时间
    private int sessionTimeout = 20000;
    // 监听
    private ZooKeeper zooKeeper;

    @Before
    public void init() throws IOException {
        // 连接zk集群
        zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                // 如果监控的话 通过以下代码
                List<String> children;
                try {
                    children = zooKeeper.getChildren("/", true);

                    for (String child : children) {
                        System.out.println(child);
                    }
                    System.out.println("-------------");
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // 创建节点
    @Test
    public void createZnode() throws KeeperException, InterruptedException {

        String path = zooKeeper.create("/apitest", "java".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        System.out.println(path);
    }

    // 获取子节点并监控数据的变化
    @Test
    public void GetDataAndWatch() throws KeeperException, InterruptedException {

//        List<String> children = zooKeeper.getChildren("/", true);
//        for (String child : children) {
//            System.out.println(child);
//        }

        Thread.sleep(Long.MAX_VALUE);
    }

    // 查看节点是否存在
    @Test
    public void GetDataExists() throws KeeperException, InterruptedException {

        Stat exists = zooKeeper.exists("/apitest", false);

        System.out.println(exists);
    }
}
