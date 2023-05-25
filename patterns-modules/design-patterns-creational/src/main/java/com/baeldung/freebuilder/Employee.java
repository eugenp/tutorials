package com.baeldung.freebuilder;

import org.inferred.freebuilder.FreeBuilder;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@FreeBuilder
public interface Employee {

    String getName();

    int getAge();

    String getDepartment();

    String getRole();

    String getSupervisorName();

    String getDesignation();

    String getEmail();

    long getPhoneNumber();

    Optional<Boolean> getPermanent();

    Optional<String> getDateOfJoining();

    @Nullable
    String getCurrentProject();

    Address getAddress();

    List<Long> getAccessTokens();

    Map<String, Long> getAssetsSerialIdMapping();

    Optional<Double> getSalaryInUSD();


    class Builder extends Employee_Builder {

        public Builder() {
            // setting default value for department
            setDepartment("Builder Pattern");
        }

        @Override
        public Builder setEmail(String email) {
            if (checkValidEmail(email))
                return super.setEmail(email);
            else
                throw new IllegalArgumentException("Invalid email");

        }

        private boolean checkValidEmail(String email) {
            return email.contains("@");
        }
    }
}
