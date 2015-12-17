import com.baeldung.guava.entity.Administrator;
import com.baeldung.guava.entity.Player;
import com.baeldung.guava.entity.User;
import com.google.common.base.Functions;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.net.InetAddresses;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.Assert;
import org.junit.Test;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.hamcrest.CoreMatchers.equalTo;

public class GuavaTest {

    private static final int ADULT_AGE = 18;

    @Test
    public void whenToString_shouldIncludeAllFields() throws Exception {
        User user = new User(12L, "John Doe", 25);

        Assert.assertThat(user.toString(), equalTo("User{id=12, name=John Doe, age=25}"));
    }

    @Test
    public void whenPlayerToString_shouldCallParentToString() throws Exception {
        User user = new Player(12L, "John Doe", 25);

        Assert.assertThat(user.toString(), equalTo("User{id=12, name=John Doe, age=25}"));
    }

    @Test
    public void whenAdministratorToString_shouldExecuteAdministratorToString() throws Exception {
        User user = new Administrator(12L, "John Doe", 25);

        Assert.assertThat(user.toString(), equalTo("Administrator{id=12, name=John Doe, age=25}"));
    }

    @Test
    public void whenFilteringByAge_shouldFilterOnlyAdultUsers() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "John", 45));
        users.add(new User(2L, "Michael", 27));
        users.add(new User(3L, "Max", 16));
        users.add(new User(4L, "Bob", 10));
        users.add(new User(5L, "Bill", 65));

        Predicate<User> byAge = input -> input.getAge() > ADULT_AGE;

        List<String> results = FluentIterable.from(users)
                .filter(byAge)
                .transform(Functions.toStringFunction())
                .toList();

        Assert.assertThat(results.size(), equalTo(3));
    }

    @Test
    public void whenCreatingFluentIterableFromArray_shouldContainAllUsers() throws Exception {
        User[] usersArray = {new User(1L, "John", 45), new User(2L, "Max", 15)} ;
        FluentIterable<User> users = FluentIterable.of(usersArray);

        Assert.assertThat(users.size(), equalTo(2));
    }

    @Test
    public void whenAppendingElementsToFluentIterable_shouldContainAllUsers() throws Exception {
        User[] usersArray = {new User(1L, "John", 45), new User(2L, "Max", 15)};

        FluentIterable<User> users = FluentIterable.of(usersArray).append(
                new User(3L, "Bob", 23),
                new User(4L, "Bill", 17)
        );

        Assert.assertThat(users.size(), equalTo(4));
    }

    @Test
    public void whenAppendingListToFluentIterable_shouldContainAllUsers() throws Exception {
        User[] usersArray = {new User(1L, "John", 45), new User(2L, "Max", 15)};

        List<User> usersList = new ArrayList<>();
        usersList.add(new User(3L, "David", 32));

        FluentIterable<User> users = FluentIterable.of(usersArray).append(usersList);

        Assert.assertThat(users.size(), equalTo(3));
    }

    @Test
    public void whenJoiningFluentIterableElements_shouldOutputAllUsers() throws Exception {
        User[] usersArray = {new User(1L, "John", 45), new User(2L, "Max", 15)};

        FluentIterable<User> users = FluentIterable.of(usersArray);

        Assert.assertThat(users.join(Joiner.on("; ")),
                equalTo("User{id=1, name=John, age=45}; User{id=2, name=Max, age=15}"));
    }

    @Test
    public void whenHashingData_shouldReturnCorrectHashCode() throws Exception {
        int receivedData = 123;

        HashCode hashCode = Hashing.crc32c().hashInt(receivedData);
        Assert.assertThat(hashCode.toString(), equalTo("495be649"));
    }

    @Test
    public void whenDecrementingIpAddress_shouldReturnOneLessIpAddress() throws Exception {
        InetAddress address = InetAddress.getByName("127.0.0.5");
        InetAddress decrementedAddress = InetAddresses.decrement(address);

        Assert.assertThat(decrementedAddress.toString(), equalTo("/127.0.0.4"));

    }

    @Test
    public void whenExecutingRunnableInThread_shouldLogThreadExecution() throws Exception {
        ConcurrentHashMap<String, Boolean> threadExecutions = new ConcurrentHashMap<>();
        Runnable logThreadRun = () -> threadExecutions.put(Thread.currentThread().getName(), true);

        Thread t = new Thread(logThreadRun);
        t.run();

        Assert.assertTrue(threadExecutions.get("main"));
    }

    @Test
    public void whenExecutingRunnableInThreadPool_shouldLogAllThreadsExecutions() throws Exception {
        ConcurrentHashMap<String, Boolean> threadExecutions = new ConcurrentHashMap<>();

        Runnable logThreadRun= () -> threadExecutions.put(Thread.currentThread().getName(), true);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(logThreadRun);
        executorService.submit(logThreadRun);
        executorService.shutdown();

        executorService.awaitTermination(100, TimeUnit.MILLISECONDS);

        Assert.assertTrue(threadExecutions.get("pool-1-thread-1"));
        Assert.assertTrue(threadExecutions.get("pool-1-thread-2"));
    }

    @Test
    public void whenExecutingRunnableInDirectExecutor_shouldLogThreadExecution() throws Exception {
        ConcurrentHashMap<String, Boolean> threadExecutions = new ConcurrentHashMap<>();

        Runnable logThreadRun= () -> threadExecutions.put(Thread.currentThread().getName(), true);

        Executor executor = MoreExecutors.directExecutor();
        executor.execute(logThreadRun);

        Assert.assertTrue(threadExecutions.get("main"));
    }

    @Test
    public void whenExecutingRunnableInListeningExecutor_shouldLogThreadExecution() throws Exception {
        ConcurrentHashMap<String, Boolean> threadExecutions = new ConcurrentHashMap<>();

        Runnable logThreadRun = () -> threadExecutions.put(Thread.currentThread().getName(), true);

        ListeningExecutorService executor = MoreExecutors.newDirectExecutorService();
        executor.execute(logThreadRun);

        Assert.assertTrue(threadExecutions.get("main"));
    }
}