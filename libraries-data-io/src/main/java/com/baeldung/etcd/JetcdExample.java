package com.baeldung.etcd;

import java.util.concurrent.CompletableFuture;

import io.etcd.jetcd.kv.DeleteResponse;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.kv.PutResponse;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.Client;

public class JetcdExample {
    public static void main(String[] args) {
        String etcdEndpoint = "http://localhost:2379";
        // String key = "/mykey";
        // String value = "Hello, etcd!";

        try (Client client = Client.builder().endpoints(etcdEndpoint).build()) {
            KV kvClient = client.getKVClient();
            ByteSequence key = ByteSequence.from("test_key".getBytes());
            ByteSequence value = ByteSequence.from("test_value".getBytes());
            
            // put the key-value
            kvClient.put(key, value).get();
            
            // get the CompletableFuture
            CompletableFuture<GetResponse> getFuture = kvClient.get(key);
            
            // get the value from CompletableFuture
            GetResponse response = getFuture.get();
            
            // delete the key
            kvClient.delete(key).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
