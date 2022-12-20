package com.baeldung.shallowdeep;

import org.junit.Assert;
import org.junit.Test;

public class DeepShallowCopyUnitTest {

	@Test
	public void givenImmutable_whenShallowCopied_thenObjectsShouldBeDifferent() {

		Engine engine = new Engine("Four-stroked", 130);
		Car car = new Car(123, engine, "Toyota Corolla");

		Car shallowCopiedCar = new Car(car.getModelNumber(), car.getEngine(), car.getModelName());
		car.setModelName("Modified-Toyota Corolla");
		Assert.assertNotEquals(car.getModelName(), shallowCopiedCar.getModelName());

	}

	@Test
	public void whenShallowCopied_thenObjectsShouldBeDifferent() {

		Engine engine = new Engine("Four-stroked", 130);
		Car car = new Car(123, engine, "Toyota Corolla");
		Car shallowCopiedCar = new Car(car.getModelNumber(), car.getEngine(), car.getModelName());
		engine.setType("Two-Stroked");
		Assert.assertSame(car.getEngine().getType(), shallowCopiedCar.getEngine().getType());

	}

	@Test
	public void whenShallowCopied_thenComponentObjectsShouldBeSame() {

		Engine engine = new Engine("Four-stroked", 130);
		Car car = new Car(123, engine, "Toyota Corolla");

		Car shallowCopiedCar = new Car(car.getModelNumber(), car.getEngine(), car.getModelName());
		car.getEngine().setType("Two-stroked");
		Assert.assertEquals(car.getEngine().getType(), shallowCopiedCar.getEngine().getType());

	}

	@Test
	public void givenCopyConstructor_whenDeepCopied_ObjectsShouldBeDifferent() {
		Engine engine = new Engine("Four-stroked", 130);
		Car car = new Car(123, engine, "Toyota Corolla");
		Car deepCopiedCar = new Car(car);
		car.getEngine().setType("Two-stroked");
		Assert.assertNotEquals(car.getEngine().getType(), deepCopiedCar.getEngine().getType());
	}

	@Test
	public void whenCloneCopied_ObjectsShouldBeDifferent() {
		Engine engine = new Engine("Four-stroked", 130);
		Car car = new Car(123, engine, "Toyota Corolla");
		Car deepCopiedCar = (Car) car.clone();
		car.getEngine().setType("Two-stroked");
		Assert.assertNotEquals(car.getEngine().getType(), deepCopiedCar.getEngine().getType());
	}

}
