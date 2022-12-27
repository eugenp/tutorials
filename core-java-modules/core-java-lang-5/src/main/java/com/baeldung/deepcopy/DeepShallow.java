package com.baeldung.deepcopy;

public class DeepShallow{
	
	public static Diagram createDiagramUsingCopyConstructor(Diagram d) {
		return new Diagram(d);
	}
	
	public static Diagram createDiagramUsingShallowCopy(Diagram d) throws CloneNotSupportedException {
		return (Diagram)d.clone();
	}
	
	public static Drawing createDrawingUsingShallowCopy(Drawing d) throws CloneNotSupportedException {
		return (Drawing)d.clone();
	}
	
	public static Collection createCollectionUsingDeepCopy(Collection d) throws CloneNotSupportedException {
		return (Collection)d.clone();
	}
	
}

class Collection implements Cloneable{
	Diagram diagram;
	Collection(Diagram diagram){
		this.diagram = diagram;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Diagram diagram = (Diagram)this.diagram.clone();
		Collection c = new Collection(diagram);
		return c;
	}
}

class Drawing implements Cloneable{
	Diagram diagram;
	Drawing(Diagram d){
		this.diagram = d;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}

class Diagram implements Cloneable{
	String name;
	String color;
	Integer width;
	Integer height;
	
	Diagram(){
		
	}

	public Diagram(String name, String color, Integer width, Integer height) {
		this.name = name;
		this.color = color;
		this.width = width;
		this.height = height;
	}
	
	public Diagram(Diagram d) {
		this.name = d.name;
		this.color = d.color;
		this.width = d.width;
		this.height = d.height;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}

