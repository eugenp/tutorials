package com.baeldung.etcd;

import io.etcd.jetcd.kv.DeleteResponse;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.kv.PutResponse;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.Client;

public class JetcdExample {
    public static void main(String[] args) {
        String etcdEndpoint = "http://localhost:2379";
        String key = "/mykey";
        String value = "Hello, etcd!";

        try (Client client = Client.builder().endpoints(etcdEndpoint).build()) {
            KV kvClient = client.getKVClient();
            ByteSequence keyBytes = ByteSequence.from(key.getBytes());
            ByteSequence valueBytes = ByteSequence.from(value.getBytes());
            
            // Put a key-value pair
            PutResponse response = kvClient.put(keyBytes, valueBytes).get();

            // Retrieve the value
            GetResponse getResponse = kvClient.get(keyBytes).get();
            String retrievedValue = getResponse.getKvs(0).getValue().toStringUtf8();
            System.out.println(retrievedValue);

            // Delete the key
            DeleteResponse deleteResponse = kvClient.delete(keyBytes).getDeleted();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
