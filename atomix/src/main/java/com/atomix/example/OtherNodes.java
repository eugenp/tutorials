package com.atomix.example;

import io.atomix.AtomixReplica;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.concurrent.DistributedLock;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class OtherNodes {

    public static void main(String[] args) throws InterruptedException {
        List<Address> cluster = Arrays
          .asList(
            new Address("localhost", 8700),
            new Address("localhost", 8701),
            new Address("localhost", 8702));

        Storage storage = Storage.builder()
          .withDirectory(new File("log"))
          .withStorageLevel(StorageLevel.DISK)
          .build();

        AtomixReplica replica2 = AtomixReplica.builder(new Address("localhost", 8701))
          .withStorage(storage)
          .withTransport(new NettyTransport())
          .build();

        WorkerThread WT1 = new WorkerThread(replica2, cluster);
        WT1.run();

        AtomixReplica replica3 = AtomixReplica.builder(new Address("localhost", 8702))
          .withStorage(storage)
          .withTransport(new NettyTransport())
          .build();

        WorkerThread WT2 = new WorkerThread(replica3, cluster);
        WT2.run();

        Thread.sleep(6000);

        DistributedLock lock = replica2.getLock("my-lock")
          .join();
        lock.lock()
          .thenRun(() -> System.out.println("Acquired a lock"));

        replica2.getMap("map")
          .thenCompose(m -> m.put("bar", "Hello world!"))
          .thenRun(() -> System.out.println("Value is set in Distributed Map"))
          .join();
    }

    private static class WorkerThread extends Thread {
        private AtomixReplica replica;
        private List<Address> cluster;

        WorkerThread(AtomixReplica replica, List<Address> cluster) {
            this.replica = replica;
            this.cluster = cluster;
        }

        public void run() {
            replica.join(cluster)
              .join();
        }
    }
}