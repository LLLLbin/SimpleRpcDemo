package com.lbin.rpc.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserDao extends Remote {
    public void test() throws RemoteException;
}
