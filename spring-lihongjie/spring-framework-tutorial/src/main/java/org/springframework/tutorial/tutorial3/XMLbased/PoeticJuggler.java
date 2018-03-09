package org.springframework.tutorial.tutorial3.XMLbased;

public class PoeticJuggler extends Juggler {

	private Poem poem;

	public PoeticJuggler(Poem poem) {
		super();
		this.poem = poem;
	}

	public PoeticJuggler(int beanBags, Poem poem) {
		super(beanBags);
		this.poem = poem;

	}

	@Override
	public void perform() {
		super.perform();
		System.out.println("While reciting ...");
		poem.recite();
	}
	
	

	
	
	
}
