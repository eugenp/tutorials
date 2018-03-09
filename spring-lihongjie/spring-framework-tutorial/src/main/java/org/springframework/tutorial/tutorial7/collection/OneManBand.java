package org.springframework.tutorial.tutorial7.collection;

import java.util.Collection;

import org.springframework.tutorial.tutorial3.XMLbased.Performer;
import org.springframework.tutorial.tutorial7.Instrument;

public class OneManBand implements Performer {

	private Collection<Instrument> instruments;
	
	
	public void setInstruments(Collection<Instrument> instruments) {
		
		this.instruments = instruments; // 注入集合
	}
	
	public Collection<Instrument> getInstruments() {
		return instruments;
	}
	
	@Override
	public void recite() {
		
		
	}

	@Override
	public void perform() {
	
	}

}
