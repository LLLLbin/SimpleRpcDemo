package com.lbin.rmi;

import com.lbin.rmi.api.FirstInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientMainClass {
    public static void main(String[] args) {
        FirstInterface first=null;
        try {
            first= (FirstInterface) Naming.lookup("rmi://localhost:9999/first");
            System.out.println(first);
            System.out.println(first.first("hello world"));
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
