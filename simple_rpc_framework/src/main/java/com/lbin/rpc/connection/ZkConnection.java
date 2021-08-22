package com.lbin.rpc.connection;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

@Data
@AllArgsConstructor
public class ZkConnection {
    private String zkServer;
    private Integer sessionTimeout;
    private ZooKeeper keeper;

    public ZkConnection() {
        zkServer="39.108.150.106:2181";
        sessionTimeout=10000;
    }

    public ZooKeeper getConnetion() throws IOException {

        keeper = new ZooKeeper(zkServer, sessionTimeout, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
            }
        });
        return keeper;
    }
}
