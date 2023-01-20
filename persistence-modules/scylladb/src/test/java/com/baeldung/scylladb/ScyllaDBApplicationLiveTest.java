package com.baeldung.scylladb;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.baeldung.scylladb.model.User;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ScyllaDBApplicationLiveTest {

    private static final String IMAGE_NAME = "scylladb/scylla";
    private static final int hostPort = 9042;
    private static final int containerExposedPort = 9042;
    private static Consumer<CreateContainerCmd> cmd = e -> e.withPortBindings(new PortBinding(Ports.Binding.bindPort(hostPort),
                                            new ExposedPort(containerExposedPort)));

    @Container
    private static final GenericContainer scyllaDbContainer = new GenericContainer(DockerImageName.parse(IMAGE_NAME))
                                                            .withExposedPorts(containerExposedPort)
                                                            .withCreateContainerCmdModifier(cmd);

    private ScylladbApplication scylladbApplication;

    @BeforeAll
    void setUp() {
        scylladbApplication = new ScylladbApplication("baeldung", "User");
    }

    @Test
    public void givenKeySpaceAndTable_whenInsertData_thenShouldBeAbleToFindData() {
        User user = new User(10, "John");
        scylladbApplication.addNewUser(user);

        List<User> userList = scylladbApplication.getUsersByUserName("John");
        assertEquals(1, userList.size());
        assertEquals("John", userList.get(0).getName());
        assertEquals(10, userList.get(0).getId());

    }

    @Test
    public void givenKeySpaceAndTable_whenInsertData_thenRowCountIncreases() {
        int initialCount = scylladbApplication.getAllUserNames().size();
        User user = new User(11, "Doe");
        scylladbApplication.addNewUser(user);

        int expectedCount = initialCount+1;
        int updatedCount = scylladbApplication.getAllUserNames().size();
        assertEquals(expectedCount, updatedCount);

    }

}
