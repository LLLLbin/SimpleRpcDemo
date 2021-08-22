package com.lbin.rpc.api.impl;

import com.lbin.rpc.api.UserDao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserDaoImpl extends UnicastRemoteObject implements UserDao {
    public UserDaoImpl() throws RemoteException {
    }

    public void test() throws RemoteException {
        System.out.println("实现类被执行了");
    }
}
