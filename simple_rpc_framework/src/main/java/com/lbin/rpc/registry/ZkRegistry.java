package com.lbin.rpc.registry;

import com.lbin.rpc.connection.ZkConnection;
import lombok.Data;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

@Data
public class ZkRegistry {
    private static Logger logger = LoggerFactory.getLogger(ZkRegistry.class);
    private ZkConnection connection;
    private String ip;
    private int port;

    public ZkRegistry(ZkConnection connection, String ip, int port) {
        this.connection = connection;
        this.ip = ip;
        this.port = port;
        logger.info("注册中心初始化成功...");
    }

    /**
     * @param serviceInterface rmi实现的接口类对象
     * @param serviceObject    真实实现了接口的对象
     * @throws IOException
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void registerService(Class<? extends Remote> serviceInterface, Remote serviceObject) throws IOException, KeeperException, InterruptedException {
        String rmi = "rmi://" + ip + ":" + port + "/" + serviceInterface.getName();
        String path = "/SimpleRpc/" + serviceInterface.getName();
        //判断路径下是否已经存在结点，若存在则删掉
        List<String> list = connection.getConnetion().getChildren("/SimpleRpc", false);
        if (list.contains(path)) {
            Stat stat = new Stat();
            connection.getConnetion().getData(path, null, stat);
            connection.getConnetion().delete(path, stat.getCversion());
        }
        //在Zookeeper下创建RPC路径并存放对应的数据
        String result = connection.getConnetion().create(path, rmi.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        logger.info("创建的结果为:" + result);
        Registry registry = LocateRegistry.createRegistry(port);
        //把真实的服务对象放入RMI的registry中
        Naming.rebind(rmi, serviceObject);
        logger.info(serviceInterface.getName() + "接口注册成功");
    }

    /**
     * 根据传入的接口类型，获得RMI的远程代理对象
     *
     * @return
     */
//    public <T extends Remote> T getServiceProxy(Class<T> serviceInterface) throws IOException, KeeperException, InterruptedException {
//        String path = "SimpleRpc" + serviceInterface.getName();
//        byte[] data = connection.getConnetion().getData(path, false, null);
//        String s = new String(data);
//
//    }
}
