package com.baeldung.shallowdeepcopy;

public class Main implements Cloneable {
	private Dependency dependency;
	 	
	public Main(Dependency dependency) {
		super();
		this.dependency = dependency;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public Object deepClone() throws CloneNotSupportedException {
		Main dependentClone = (Main) super.clone();
		Dependency dependencyClone = (Dependency) this.dependency.clone();
		dependentClone.setDependency(dependencyClone);
		return dependentClone;
	}
	
	public Dependency getDependency() {
		return dependency;
	}

	public void setDependency(Dependency dependency) {
		this.dependency = dependency;
	}
	
}
