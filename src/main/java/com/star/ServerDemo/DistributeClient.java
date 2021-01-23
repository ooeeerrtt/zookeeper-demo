package com.star.ServerDemo;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DistributeClient {
    private String connectString = "192.168.140.101:2181,192.168.140.102:2181,192.168.140.103:2181";
    private int sessionTimeout = 20000;
    private ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        DistributeClient client = new DistributeClient();

        client.getConnect();

        client.getChildren();

        client.business();

    }

    private void getConnect() throws IOException {
        zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    getChildren();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getChildren() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren("/servers", true);

        ArrayList<Object> arrayList = new ArrayList<>();

        for (String child : children) {
            byte[] data = zooKeeper.getData("/servers/" + child, false, null);
            arrayList.add(new String(data));
        }

        System.out.println(arrayList);
    }

    public void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }
}
