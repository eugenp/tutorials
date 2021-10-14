package org.cloud.app.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CLOUDTECH")
@Data
public class CloudTechEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column
    private Long cloudTechId;

    @Column
    private String cloudTech;

    @Column
    private String cloud;

}
