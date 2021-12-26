package br.com.baeldung.operators;

import br.com.baeldung.model.Airplane;
import br.com.baeldung.model.Car;
import br.com.baeldung.model.Transport;

public class Comparison {
	
	public static void  verifyIfCarIsAnInstanceOfTransport() {
		Object car = new Car();
		//It will return true, because Car is a class of the type Transport.
		System.out.println(car instanceof Transport);
	}
	
	public static void  verifyIfAirplaneIsAnInstanceOfTransport() {
		Object airplane = new Airplane();
		//It will return true, because Airplane is a class of the type Transport.
		System.out.println(airplane instanceof Transport);
	}
	
	public static void  verifyIfAirplaneIsAnInstanceOfCar()  {
	
		Object airplane = new Airplane();
		//It will return false.  Although Car and Airplane extend Transport, they are different classes.
		System.out.println(airplane instanceof Car);
	}
	
	public static void  verifyTransportInstanceAsAirplane() {
		Transport transport = new Airplane();
		//It will return true, because Airplane is a class of the type Transport.
		System.out.println(transport instanceof Airplane);
	}
	
	public static void  verifyTransportInstanceAsCar() {
		Transport transport = new Car();
		//It will return true, because Car is a class of the type Transport.
		System.out.println(transport instanceof Car);
	}
	
	public static void  verifyTransportInstanceAsCarButInstantiatedAsAirplane() {
		Transport transport = new Airplane();
		//It will return false, because Car and Airplane are different class.
		System.out.println(transport instanceof Car);
	}
	
	
	public static void  verifyTransportInstanceAsAirplaneButInstantiatedAsCar() {
		Transport transport = new Car();
		//It will return false, because Car and Airplane are different class.
		System.out.println(transport instanceof Airplane);
	}

}
