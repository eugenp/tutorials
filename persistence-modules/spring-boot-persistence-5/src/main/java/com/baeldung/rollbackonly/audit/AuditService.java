package com.baeldung.rollbackonly.audit;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AuditService {

    private final AuditRepo auditRepo;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAudit(String action, String status, String message) {
        auditRepo.save(new Audit(action, status, message));
    }

    public AuditService(AuditRepo auditRepo) {
        this.auditRepo = auditRepo;
    }
}