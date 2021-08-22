package com.lbin.rpc.invoke;

import com.lbin.rpc.connection.ZkConnection;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;

public class Invocation {
    public <T extends Remote>T getServiceProxy(Class<T> clazz) throws IOException, KeeperException, InterruptedException, NotBoundException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        ZooKeeper keeper = new ZkConnection().getConnetion();
        byte[] data = keeper.getData("/SimpleRpc/" + clazz.getName(), null, null);
        String rmiPath = new String(data);
        Remote remote = Naming.lookup(rmiPath);
        return (T)remote;
    }
}
