package com.lbin.rmi.impl;

import com.lbin.rmi.api.FirstInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FirtstRMIImpl extends UnicastRemoteObject implements FirstInterface {

    public FirtstRMIImpl() throws RemoteException {
        super();
    }

    public String first(String name) throws RemoteException {
        System.out.println("客户端的请求参数为"+name);
        return "你好" + name;
    }
}
