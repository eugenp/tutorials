package com.baeldung.collections.pecs;

import java.util.Arrays;
import java.util.List;

import com.baeldung.collections.pecs.model.Customer;
import com.baeldung.collections.pecs.model.Operator;
import com.baeldung.collections.pecs.model.User;

public class ProducerExtendsConsumerSupers {

    public void producerExtends() {
        List<Operator> operators = Arrays.asList(new Operator("sam"), new Operator("daniel"));
        List<Customer> customers = Arrays.asList(new Customer("arys"), new Customer("cristiana"));

        // sendEmails(operators); --> will not compile!
        sendEmailsFixed(operators);
        sendEmailsFixed(customers);
    }

    private void sendEmails(List<User> users) {
        for (User user : users) {
            System.out.println("sending email to " + user);
        }
    }

    private void sendEmailsFixed(List<? extends User> users) {
        for (User user : users) {
            System.out.println("sending email to " + user);
        }
    }

    public void consumerSupers() {
        List<Operator> allOperators = Arrays.asList(new Operator("tom"));
        List<User> allUsers = Arrays.asList(new Operator("tom"), new Customer("spencer"));

        // addUsersFromMarketingDepartment(allUsers); --> will not compile!
        addUsersFromMarketingDepartmentFixed(allOperators);
        addUsersFromMarketingDepartmentFixed(allUsers);
    }

    private void addUsersFromMarketingDepartment(List<Operator> users) {
        users.add(new Operator("john doe"));
        users.add(new Operator("jane doe"));
    }

    private void addUsersFromMarketingDepartmentFixed(List<? super Operator> users) {
        users.add(new Operator("john doe"));
        users.add(new Operator("jane doe"));
    }

    private void addUsersAndSendEmails(List<User> users) {
        users.add(new Operator("john doe"));
        for (User user : users) {
            System.out.println("sending email to: " + user);
        }
    }

}