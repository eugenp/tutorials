package com.baeldung.hexagonalarchitecture2.appointments.scheduling.adapter.outbound.persistence;

import com.baeldung.hexagonalarchitecture2.appointments.scheduling.common.GeneratedId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("appointments")
public class AppointmentEntity implements GeneratedId {
    @Id
    private String id;
    private LocalDateTime appointmentTime;
    private String requesterEmail;
    @CreatedDate
    private Instant createdDate;
    @LastModifiedDate
    private Instant lastModifiedDate;
}
