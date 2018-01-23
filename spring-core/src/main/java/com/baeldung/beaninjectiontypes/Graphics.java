package com.baeldung.beaninjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;


public class Graphics {

	
	private Surface surface;
	
	public Graphics(Surface surface){
		this.surface = surface;
	}
	
	public void setSurface(Surface surface){
		this.surface = surface;
	}
	
	public Surface getSurface(){
		return this.surface;
	}
}
