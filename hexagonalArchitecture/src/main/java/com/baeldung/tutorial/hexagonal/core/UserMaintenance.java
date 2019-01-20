package com.baeldung.tutorial.hexagonal.core;

import com.baeldung.tutorial.hexagonal.port.in.UserRegistration;
import com.baeldung.tutorial.hexagonal.port.out.UserRepository;
import lombok.Setter;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This is the core part of the application where actual business logic is applied.
 * As this is an example a simple data is maintained.
 * This core part handles on generating the unique id and setting the UserType.
 */
public class UserMaintenance {

    //Ideally this should be autowired.
    @Setter
    private UserRepository userRepository;

    private static AtomicLong unqiueIdGenerator = new AtomicLong(20000L);

    /**
     * Register the user by populating the data required.
     * @param userRegistration
     * @return
     */
    public boolean registerUser(UserRegistration userRegistration) {
        userRepository.saveUser(createUser(userRegistration));
        return false;
    }

    private User createUser(UserRegistration userRegistration) {
        User user = new User();
        user.setAddress(userRegistration.getUserAddress());
        user.setEmail(userRegistration.getUserEmail());
        user.setName(userRegistration.getUserName());
        user.setUserType(userRegistration.subscriptionPaid() ? UserType.PAID_USER : UserType.FREE_USER);
        user.setUniqueId(unqiueIdGenerator.getAndIncrement());
        user.setUserRegisteredDate(new Date(System.currentTimeMillis()));
        return user;
    }

    /**
     * retrieve the user details from repository
     * @param userEmailId
     * @return
     */
    public User getUser(String userEmailId) {
        User user = userRepository.getUser(userEmailId);
        if (!UserType.PREMIUM_USER.equals(user.getUserType()) && isConvertableToPremium(user.getUserRegisteredDate())) {
            user.setUserType(UserType.PREMIUM_USER);
            userRepository.saveUser(user);
        }
        return user;
    }

    /**
     * A core business logic to get the UserType.
     * @param userRegistrationDate
     * @return
     */
    private boolean isConvertableToPremium(Date userRegistrationDate) {
        Date currentDate = new Date(System.currentTimeMillis());
        return TimeUnit.MILLISECONDS.toDays(currentDate.getTime() - userRegistrationDate.getTime()) >= 90 ? true : false;
    }

    public boolean deleteUser(String userEmailId) {
        return userRepository.deleteUser(userEmailId);
    }

    public Long getUserUniqueId(String userEmailId) {
        return userRepository.getUser(userEmailId).getUniqueId();
    }
}
