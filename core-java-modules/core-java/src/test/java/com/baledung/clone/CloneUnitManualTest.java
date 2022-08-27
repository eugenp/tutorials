package com.baledung.clone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import com.baeldung.clone.Driver;
import com.baeldung.clone.DriverWithDeepCopy;
import com.baeldung.clone.Name;

public class CloneUnitManualTest {
	@Test
    public void givenClone_whenShallowCopy_thenNewCopy() throws CloneNotSupportedException {
		
		Name name = new Name("Ajay", "Veluru");				
		Driver driver = new Driver(01, name, "DL1234567890");

		Driver driver02 = (Driver) driver.clone();
		
		driver.setId(2);
		driver.getName().setFirstName("John");
		driver.getName().setLastName("Marc");
		driver.setLicenseId("DL9999999999");
		
		assertNotEquals(driver.getId(), driver02.getId());
		assertNotEquals(driver.getLicenseId(), driver02.getLicenseId());
		assertEquals(driver.getName(), driver02.getName());
	}
	
	@Test
    public void givenClone_whenDeepCopy_thenNewCopy() throws CloneNotSupportedException {
		Name name = new Name("Ajay", "Veluru");				
		DriverWithDeepCopy driver = new DriverWithDeepCopy(01, name, "DL1234567890");

		DriverWithDeepCopy driver02 = (DriverWithDeepCopy) driver.clone();
		
		driver.setId(2);
		driver.getName().setFirstName("John");
		driver.getName().setLastName("Marc");
		driver.setLicenseId("DL9999999999");
		
		assertNotEquals(driver.getId(), driver02.getId());
		assertNotEquals(driver.getLicenseId(), driver02.getLicenseId());
		assertNotEquals(driver.getName(), driver02.getName());
    }
}
