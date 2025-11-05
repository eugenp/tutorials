package com.baeldung.rollbackonly.audit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String operation;

    private String status;

    private String description;

    private LocalDateTime timestamp;

    public Audit(String operation, String status, String description) {
        this.operation = operation;
        this.status = status;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

}
