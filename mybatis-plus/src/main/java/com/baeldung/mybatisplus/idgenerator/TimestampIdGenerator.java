package com.baeldung.mybatisplus.idgenerator;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.stereotype.Component;

@Component
public class TimestampIdGenerator implements IdentifierGenerator {

    @Override
    public Long nextId(Object entity) {
        return System.nanoTime();
    }

}