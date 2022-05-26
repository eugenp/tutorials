package test.java.com.baeldung.tutorials;

import org.junit.Assert;
import org.junit.Test;


import main.java.com.baeldung.tutorials.Computer;
import main.java.com.baeldung.tutorials.PersonalizedComputer;
import main.java.com.baeldung.tutorials.Processor;

public class ObjectCopyUnitTest {

    @Test
    public void whenUpdatedShallowCopy_thenOriginalShouldbeUpdated() throws CloneNotSupportedException {
    	Processor processor = new Processor("Microsoft", "i55");
		Computer originalComputer = new Computer(2048, "model11", processor);
		Computer copiedComputer = (Computer) originalComputer.clone();

		copiedComputer.getProcessor().setCompany("Google");
		copiedComputer.getProcessor().setVersion("i100");
		copiedComputer.setMemory(1234);

	    Assert.assertEquals(originalComputer.getProcessor().getCompany(),
	    		copiedComputer.getProcessor().getCompany());
	    Assert.assertEquals(originalComputer.getProcessor().getVersion(),
	    		copiedComputer.getProcessor().getVersion());
	    //Primitive type values if changed then should NOT be same in the shallow copied object
	    Assert.assertNotEquals(originalComputer.getMemory(), copiedComputer.getMemory());
    }
    
    @Test
    public void whenUpdatedDeepCopy_thenOriginalShouldNotbeUpdated() throws CloneNotSupportedException {
    	Processor processor = new Processor("Intel", "i5");
		PersonalizedComputer originalPComputer = new PersonalizedComputer(2048, "model1", processor);
		PersonalizedComputer copiedPComputer = (PersonalizedComputer) originalPComputer.clone();

		copiedPComputer.getProcessor().setCompany("Google");
		copiedPComputer.getProcessor().setVersion("i100");
		copiedPComputer.setMemory(1234);

	    Assert.assertNotEquals(originalPComputer.getProcessor().getCompany(),
	    		copiedPComputer.getProcessor().getCompany());
	    Assert.assertNotEquals(originalPComputer.getProcessor().getVersion(),
	    		copiedPComputer.getProcessor().getVersion());
	    Assert.assertNotEquals(originalPComputer.getMemory(), copiedPComputer.getMemory());
        
    }

}
