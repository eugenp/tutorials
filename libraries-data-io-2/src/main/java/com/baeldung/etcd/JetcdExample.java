package com.baeldung.etcd;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.kv.GetResponse;

import java.util.concurrent.CompletableFuture;

public class JetcdExample {
    public static void main(String[] args) {
        String etcdEndpoint = "http://localhost:2379";
        ByteSequence key = ByteSequence.from("/mykey".getBytes());
        ByteSequence value = ByteSequence.from("Hello, etcd!".getBytes());

        try (Client client = Client.builder().endpoints(etcdEndpoint).build()) {
            KV kvClient = client.getKVClient();
            
            // Put a key-value pair
            kvClient.put(key, value).get();
            
            // Retrieve the value using CompletableFuture
            CompletableFuture<GetResponse> getFuture = kvClient.get(key);
            GetResponse response = getFuture.get();
            
            // Delete the key
            kvClient.delete(key).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
