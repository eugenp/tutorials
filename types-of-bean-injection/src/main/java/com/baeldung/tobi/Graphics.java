package com.baeldung.tobi;

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
