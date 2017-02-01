package spring.baeldung;

import org.springframework.beans.factory.annotation.Autowired;

public class FieldInjectionDemo {
	/**
	 * @Autowired annotation is used to inject dependencies. Spring framework automatically loads the dependent classes
	 */
	@Autowired
	private DependencyA dependencyA;
	@Autowired
	private DependencyB	dependencyB;
	
	public void demo(){
		System.out.println("Field Injection Demo");
		dependencyA.display();
		dependencyB.display();
	}
}
