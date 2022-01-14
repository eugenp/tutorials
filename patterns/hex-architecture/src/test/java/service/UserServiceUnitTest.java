package service;

import com.baeldung.hexagonal.adapter.MongoDatabaseAdapter;
import com.baeldung.hexagonal.adapter.PostgresDatabaseAdapter;
import com.baeldung.hexagonal.model.User;
import com.baeldung.hexagonal.port.UserDatabasePort;
import com.baeldung.hexagonal.service.UserService;
import org.junit.Assert;
import org.junit.Test;

public class UserServiceUnitTest {

    @Test
    public void findUser_ReturnsUser_MongoDatabase() {
        //Arrange
        UserDatabasePort mongoDatabasePort = new MongoDatabaseAdapter();
        UserService userService = new UserService(mongoDatabasePort);

        //Act
        String username = "test-user";
        User user = userService.findUser(username);

        //Assert
        String expectedToken = username + " Token fetched from Mongo Db";
        Assert.assertEquals(expectedToken, user.getToken());
    }

    @Test
    public void findUser_ReturnsUser_PostgresDatabase() {
        //Arrange
        UserDatabasePort postgresDatabasePort = new PostgresDatabaseAdapter();
        UserService userService = new UserService(postgresDatabasePort);

        //Act
        String username = "test-user";
        User user = userService.findUser(username);

        //Assert
        String expectedToken = username + " Token fetched from Postgres Db";
        Assert.assertEquals(expectedToken, user.getToken());
    }
}