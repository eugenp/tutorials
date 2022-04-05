/**
 * 
 */
package com.baeldung.boot.jdbi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.boot.jdbi.dao.CarMakerDao;
import com.baeldung.boot.jdbi.dao.CarModelDao;
import com.baeldung.boot.jdbi.domain.CarMaker;
import com.baeldung.boot.jdbi.domain.CarModel;

/**
 * @author Philippe
 *
 */
@Service
public class CarMakerService {
    
    private CarMakerDao carMakerDao;
    private CarModelDao carModelDao;

    public CarMakerService(CarMakerDao carMakerDao,CarModelDao carModelDao) {
        
        this.carMakerDao = carMakerDao;
        this.carModelDao = carModelDao;
    }
    
    @Transactional
    public int bulkInsert(CarMaker carMaker) {
        Long carMakerId;
        if (carMaker.getId() == null ) {
            carMakerId = carMakerDao.insert(carMaker);
            carMaker.setId(carMakerId);
        }

        // Make sure all models belong to the same maker
        carMaker.getModels().forEach(m -> {
            m.setMakerId(carMaker.getId());
            carModelDao.insert(m);
        });        
        
        return carMaker.getModels().size();
    }
}
