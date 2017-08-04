package com.baeldung.dependency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    private DataSource dataSource;
    private AuditService auditService;

    @Autowired
    public DatabaseService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DatabaseService{");
        sb.append("dataSource=")
            .append(dataSource);
        sb.append('}');
        return sb.toString();
    }
}
