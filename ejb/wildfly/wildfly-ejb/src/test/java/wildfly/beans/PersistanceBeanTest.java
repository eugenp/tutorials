package wildfly.beans;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import javax.ejb.EJB;

import model.User;

public class PersistanceBeanTest {
	
	@EJB
	UserBeanLocal users;
	
	@Test
	public void givenOneBean_whenBeanGetsData_hasTwoRecords() {
		List<User> userList = users.getUsers();
		
		Assert.assertEquals(2, userList.size());
	}

}
