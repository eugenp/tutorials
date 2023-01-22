package com.baeldung.suppliercallable.service.supplier;

import java.time.LocalDate;
import java.time.Period;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.baeldung.suppliercallable.data.User;
import com.baeldung.suppliercallable.service.Service;

public class SupplierServiceImpl implements Service {
    @Override
    public User execute(User user) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletableFuture<Integer> ageFut = CompletableFuture.supplyAsync(() -> Period.between(user.getBirthDate(), LocalDate.now())
          .getYears(), executorService)
          .exceptionally((throwable -> null));
        CompletableFuture<Boolean> canDriveACarFut = ageFut.thenComposeAsync(age -> CompletableFuture.supplyAsync(() -> age > 18, executorService))
          .exceptionally((ex) -> false);
        user.setAge(ageFut.join());
        user.setCanDriveACar(canDriveACarFut.join());
        return user;
    }

}
