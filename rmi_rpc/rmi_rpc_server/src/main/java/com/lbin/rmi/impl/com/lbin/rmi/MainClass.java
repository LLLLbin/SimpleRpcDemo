package com.lbin.rmi.impl.com.lbin.rmi;

import com.lbin.rmi.api.FirstInterface;
import com.lbin.rmi.impl.FirtstRMIImpl;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class MainClass {
    public static void main(String[] args) {
        try {
            System.out.println("服务器启动中。。。");
            FirstInterface first=new FirtstRMIImpl();
            LocateRegistry.createRegistry(9999);
            Naming.bind("rmi://localhost:9999/first",first);
            System.out.println("服务器启动完毕");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
