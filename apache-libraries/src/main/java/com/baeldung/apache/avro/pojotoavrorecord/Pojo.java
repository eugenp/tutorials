package com.baeldung.apache.avro.pojotoavrorecord;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.time.ZoneId;

public class Pojo {

    private final Map<String, String> aMap;
    private final long uid;
    private final long localDateTime;

    public Pojo() {
        aMap = new HashMap<>();

        uid = ThreadLocalRandom.current().nextLong();
        localDateTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        aMap.put("mapKey", "mapValue");
    }

    public Map<String, String> getaMap() {
        return aMap;
    }

    public long getUid() {
        return uid;
    }

    public long getLocalDateTime() {
        return localDateTime;
    }
}
