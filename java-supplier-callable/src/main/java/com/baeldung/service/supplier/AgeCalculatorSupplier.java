package com.baeldung.service.supplier;

import java.util.function.Supplier;

import com.baeldung.data.User;
import com.baeldung.service.AbstractAgeCalculator;

public class AgeCalculatorSupplier extends AbstractAgeCalculator implements Supplier<User> {

    private User user;

    @Override
    public User get() {
        user.setAge(calculateAge(user.getBirthDate()));
        return user;
    }

    public AgeCalculatorSupplier(User user) {
        this.user = user;
    }
}
