package com.baeldung.supplier_callable.service.supplier;

import java.time.LocalDate;
import java.time.Period;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.baeldung.supplier_callable.data.User;
import com.baeldung.supplier_callable.service.Service;

public class SupplierServiceImpl implements Service {
    @Override
    public User execute(User user) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletableFuture<Integer> ageFut = CompletableFuture.supplyAsync(() -> Period.between(user.getBirthDate(), LocalDate.now())
          .getYears(), executorService);
        CompletableFuture<Boolean> canDriveACarFut = ageFut.thenComposeAsync(age -> CompletableFuture.supplyAsync(() -> age > 18, executorService))
          .exceptionally((ex) -> false);
        user.setAge(ageFut.join());
        user.setCanDriveACar(canDriveACarFut.join());
        return user;
    }

}
