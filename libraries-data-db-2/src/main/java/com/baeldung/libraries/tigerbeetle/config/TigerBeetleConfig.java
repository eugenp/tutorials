package com.baeldung.libraries.tigerbeetle.config;

import com.tigerbeetle.Client;
import com.tigerbeetle.UInt128;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;

@Configuration
public class TigerBeetleConfig {

    @Value("${tigerbeetle.clusterID:0}")
    private BigInteger clusterID;

    @Value("${tb_address:3000}")
    private String[] replicaAddress;

    @Bean
    Client tigerBeetleClient() {
        return new Client(UInt128.asBytes(clusterID), replicaAddress);
    }
}
