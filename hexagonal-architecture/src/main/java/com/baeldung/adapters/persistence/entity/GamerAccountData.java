package com.baeldung.adapters.persistence.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GamerAccountData {

    @Id
    private Long id;
    private int rankLevel;

}

