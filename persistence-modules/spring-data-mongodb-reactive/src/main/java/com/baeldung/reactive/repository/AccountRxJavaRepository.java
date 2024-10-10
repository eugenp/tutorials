package com.baeldung.reactive.repository;

import com.baeldung.reactive.model.Account;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import org.springframework.data.repository.reactive.RxJava3CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRxJavaRepository extends RxJava3CrudRepository<Account, String> {

    public Observable<Account> findAllByValue(Double value);

    public Single<Account> findFirstByOwner(Single<String> owner);
}
