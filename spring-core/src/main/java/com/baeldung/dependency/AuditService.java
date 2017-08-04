package com.baeldung.dependency;

import org.springframework.stereotype.Service;

@Service
public class AuditService {

    private String level;

    public AuditService(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AuditService{");
        sb.append("level='")
            .append(level)
            .append('\'');
        sb.append('}');
        return sb.toString();
    }
}
