package com.baeldung.hexagonal.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
public class TimeSheet {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private  Long id;
	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private User User;
    private String typeRegitry;
    private LocalDateTime registeredAt;
    
    
    
    
}
