package com.baeldung.envers.customrevision.domain;

import com.baeldung.envers.customrevision.service.CustomRevisionListener;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.*;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Entity
@RevisionEntity
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(CustomRevisionListener.class)
public class CustomRevisionEntity extends DefaultRevisionEntity {

    private String remoteHost;
    private String remoteUser;
}
