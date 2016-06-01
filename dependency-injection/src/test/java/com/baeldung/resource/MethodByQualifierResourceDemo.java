package com.baeldung.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"/applicationContextTest-Resource-Qualifier.xml"})
public class MethodByQualifierResourceDemo {

	private File arbDependency;
	private File anotherArbDependency;

	@Test
	public void given_ResourceQualifier_OnSetter_THEN_ValidDependencies(){
		assertNotNull(arbDependency);
		assertEquals("namedFile.txt", arbDependency.getName());
		assertNotNull(anotherArbDependency);
		assertEquals("defaultFile.txt", anotherArbDependency.getName());
	}

	@Resource
	@Qualifier("namedFile")
	public void setArbDependency(File arbDependency) {
		this.arbDependency = arbDependency;
	}

	@Resource
	@Qualifier("defaultFile")
	public void setAnotherArbDependency(File anotherArbDependency) {
		this.anotherArbDependency = anotherArbDependency;
	}
}
