package com.star.ServerDemo;

import org.apache.zookeeper.*;

import java.io.IOException;

public class DistributeServer {

    private String connectString = "192.168.140.101:2181,192.168.140.102:2181,192.168.140.103:2181";
    private int sessionTimeout = 20000;
    private ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

        DistributeServer server = new DistributeServer();

        server.getConnect();

        server.regist("hadoop101");

        server.business();


    }

    private void getConnect() throws IOException {

        zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }

    private void regist(String postNmme) throws KeeperException, InterruptedException {
        String path = zooKeeper.create("/servers/server", postNmme.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        System.out.println(postNmme + "is online");
    }

    public void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }
}
