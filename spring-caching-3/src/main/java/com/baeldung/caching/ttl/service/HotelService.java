package com.baeldung.caching.ttl.service;

import com.baeldung.caching.ttl.repository.HotelRepository;
import com.baeldung.caching.ttl.config.GuavaCachingConfig;
import com.baeldung.caching.ttl.exception.ElementNotFoundException;
import com.baeldung.caching.ttl.model.Hotel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final GuavaCachingConfig<Hotel> hotelGuavaCachingConfig;
    Logger logger = LoggerFactory.getLogger(HotelService.class);

    HotelService(HotelRepository hotelRepository, GuavaCachingConfig<Hotel> hotelGuavaCachingConfig) {
        this.hotelRepository = hotelRepository;
        this.hotelGuavaCachingConfig = hotelGuavaCachingConfig;
    }

    @Cacheable("hotels")
    public List<Hotel> getAllHotels() {
        return hotelRepository.getAllHotels();
    }

    @CacheEvict(value = "hotels", allEntries = true)
    @Scheduled(fixedRateString = "${caching.spring.hotelListTTL}")
    public void emptyHotelsCache() {
        logger.info("emptying Hotels cache");
    }


    public Hotel getHotelById(Long hotelId) {
        if (hotelGuavaCachingConfig.get(hotelId) != null) {
            logger.info(String.format("hotel with id: %s found in cache", hotelId));
            return hotelGuavaCachingConfig.get(hotelId);
        }
        logger.info(String.format("hotel with id: %s is being searched in DB", hotelId));
        Hotel hotel = hotelRepository.getHotelById(hotelId)
                .orElseThrow(() -> new ElementNotFoundException(String.format("Hotel with id %s not found", hotelId)));
        hotelGuavaCachingConfig.add(hotelId, hotel);
        return hotel;
    }

}