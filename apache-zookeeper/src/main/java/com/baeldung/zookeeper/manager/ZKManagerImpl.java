package com.baeldung.zookeeper.manager;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import com.baeldung.zookeeper.connection.ZKConnection;

public class ZKManagerImpl implements ZKManager {
    private static ZooKeeper zkeeper;
    private static ZKConnection zkConnection;

    public ZKManagerImpl() {
        initialize();
    }

    /** * Initialize connection */
    private void initialize() {
        try {
            zkConnection = new ZKConnection();
            zkeeper = zkConnection.connect("localhost");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            zkConnection.close();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void create(String path, byte[] data) throws KeeperException, InterruptedException {
        zkeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public Object getZNodeData(String path, boolean watchFlag) {
        try {
            byte[] b = null;
            b = zkeeper.getData(path, null, null);
            String data = new String(b, "UTF-8");
            System.out.println(data);
            return data;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void update(String path, byte[] data) throws KeeperException, InterruptedException {
        int version = zkeeper.exists(path, true)
            .getVersion();
        zkeeper.setData(path, data, version);
    }
}
