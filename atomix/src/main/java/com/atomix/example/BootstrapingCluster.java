package com.atomix.example;

import io.atomix.AtomixReplica;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class BootstrapingCluster {

    public static void main(String[] args) {
        Storage storage = Storage.builder()
          .withDirectory(new File("log"))
          .withStorageLevel(StorageLevel.DISK)
          .build();
        AtomixReplica replica = AtomixReplica.builder(new Address("localhost", 8700))
          .withStorage(storage)
          .withTransport(new NettyTransport())
          .build();

        CompletableFuture<AtomixReplica> completableFuture = replica.bootstrap();
        completableFuture.join();
    }
}
