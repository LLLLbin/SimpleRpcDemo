package com.lbin.zk;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;

public class TestZookeeper {
    public static void main(String[] args) throws Exception {
        list();
    }

    /**
     * 查询节点
     */
    public static void list() throws Exception{
        ZooKeeper keeper = new ZooKeeper("39.108.150.106:2181", 10000, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println("watch方法执行");
            }
        });
        List<String> list = keeper.getChildren("/", false);
        for (String s : list) {
            System.out.println(s);
        }
    }

    /**
     * 创建客户端
     * 使用客户端发送命令
     * 处理返回结果
     * 回收资源
     */
    public static void create() throws IOException, KeeperException, InterruptedException {
        ZooKeeper keeper = new ZooKeeper("39.108.150.106:2181", 10000, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println("watch方法执行");
            }
        });
        //创建一个节点
        String result = keeper.create("/parents", "parent data".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("结果为"+result);
        //创建一个临时节点
        String tmpResult = keeper.create("/parents/tmp", null,
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("结果为"+tmpResult);
        //创建一个带序号的节点
        String seqResult = keeper.create("/parents/sequence", null,
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        System.out.println("结果为"+seqResult);
        Thread.sleep(6000);
        keeper.close();
    }
}
