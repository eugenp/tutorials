package com.baeldung.kafkastreams;

import org.apache.kafka.common.serialization.Serdes;

public class UserSerde extends Serdes.WrapperSerde<User> {
    public UserSerde() {
        super(new UserSerializer(), new UserDeserializer());
    }
}

