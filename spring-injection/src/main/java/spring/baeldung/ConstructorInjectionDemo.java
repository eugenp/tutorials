package spring.baeldung;

public class ConstructorInjectionDemo {
	private DependencyA dependencyA;
	private DependencyB dependencyB;
	
	/**
	 * All dependencies are passed as constructor arguments
	 * @param dependencyA
	 * @param dependencyB
	 */
	public ConstructorInjectionDemo(DependencyA dependencyA, DependencyB dependencyB) {
		this.dependencyA = dependencyA;
		this.dependencyB = dependencyB;
	}
	
	public void demo(){
		System.out.println("Constructor Injection Demo");
		dependencyA.display();
		dependencyB.display();
	}
}
