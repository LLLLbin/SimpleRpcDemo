package com.lbin.rmi.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FirstInterface extends Remote {
    String first(String name) throws RemoteException;
}
