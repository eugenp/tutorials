package com.baeldung.spring.data.reactive.repo;

import com.baeldung.spring.data.reactive.domain.Taxi;
import io.reactivex.Flowable;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.reactive.RxJava2CrudRepository;

/**
 * Created by shazi on 11/21/2017.
 */
public interface RxJava2TaxiRepository extends RxJava2CrudRepository<Taxi, String> {

    Flowable<Taxi> findByNumber(String number);

    @Tailable
    Flowable<Taxi> findWithTailableCursorBy();

}
