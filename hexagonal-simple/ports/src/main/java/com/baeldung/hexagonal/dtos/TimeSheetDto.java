package com.baeldung.hexagonal.dtos;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TimeSheetDto  {

	private Long id;
	private Long userId;
    private String registerType;
    private LocalDateTime registeredAt;
}