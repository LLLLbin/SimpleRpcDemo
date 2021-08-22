package com.lbin.rpc;

import com.lbin.rpc.api.UserDao;
import com.lbin.rpc.api.impl.UserDaoImpl;
import com.lbin.rpc.connection.ZkConnection;
import com.lbin.rpc.invoke.Invocation;
import com.lbin.rpc.registry.ZkRegistry;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class TestMainClass {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException, NoSuchMethodException, InstantiationException, IllegalAccessException, NotBoundException {
        ZkConnection connection = new ZkConnection();
        ZkRegistry registry = new ZkRegistry(connection,"localhost",9999);
        UserDao dao=new UserDaoImpl();
        registry.registerService(UserDao.class,dao);
        UserDao proxy = new Invocation().getServiceProxy(UserDao.class);
        proxy.test();
    }
}
