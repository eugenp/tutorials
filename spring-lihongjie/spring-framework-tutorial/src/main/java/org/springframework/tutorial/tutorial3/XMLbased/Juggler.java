package org.springframework.tutorial.tutorial3.XMLbased;

public class Juggler implements Performer {

	private int beanBags = 3;
	private int timer = 1;
	
	public Juggler() {
		
	}


	public Juggler(int beanBags) {
		this.beanBags = beanBags;
	}


	public Juggler(int beanBags, int timer) {
		super();
		this.beanBags = beanBags;
		this.timer = timer;
	}


	public void perform() {
		System.out.println("JUGGLING " + beanBags);
		System.out.println("Timer " + timer);
	}


	public void recite() {
		// TODO Auto-generated method stub
		
	}


}
