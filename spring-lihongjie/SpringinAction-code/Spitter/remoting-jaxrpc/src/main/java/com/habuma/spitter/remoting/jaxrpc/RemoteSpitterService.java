package com.habuma.spitter.remoting.jaxrpc;

import java.rmi.RemoteException;
import java.util.List;

import com.habuma.spitter.domain.Spitter;
import com.habuma.spitter.domain.Spittle;

 interface RemoteSpitterService {
  void addSpittle(Spittle arg0) throws RemoteException;
  void deleteSpittle(long arg0) throws RemoteException;
  List<Spittle> getRecentSpittles(int arg0) throws RemoteException;
  List<Spittle> getSpittlesForSpitter(Spitter arg0) throws RemoteException;
}
