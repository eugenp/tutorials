package com.baeldung.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class IPLSeasonCurrentFormatService implements IPLSeasonService{
	
	private TournamentFormat format;
	
	public List<String> getVenues() {
		return Arrays.asList("Kolkata", "Mumbai", "Mohali", "Bangalore");
	}
	
	public String getFormat(){
		return format.getName();
	}
	
	@Autowired
	public void setTournamentFormat(TournamentFormat format){
		this.format = format;
	}
}