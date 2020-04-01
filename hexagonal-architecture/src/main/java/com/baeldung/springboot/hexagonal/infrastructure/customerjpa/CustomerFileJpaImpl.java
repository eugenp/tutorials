package com.baeldung.springboot.hexagonal.infrastructure.customerjpa;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.baeldung.springboot.hexagonal.domain.Flight;
import com.baeldung.springboot.hexagonal.domain.Reservation;
import com.baeldung.springboot.hexagonal.infrastructure.CustomerFileService;

@Component
public class CustomerFileJpaImpl implements CustomerFileService {
    private ReservationFileRepository repository;

    public CustomerFileJpaImpl(ReservationFileRepository repository) {
        this.repository = repository;
    }

    @Override
    public Reservation save(Reservation booking) {
        booking.setId(UUID.randomUUID());
        return toReservation(repository.save(toFile(booking)));
    }

    @Override
    @Transactional
    public List<Reservation> bookings(Long customerId) {
        return repository.findByCustomerId(customerId).stream()
                .map(CustomerFileJpaImpl::toReservation)
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(Long customerId, UUID booking) {
        ReservationFile file = repository.findByCustomerIdAndReservationId(customerId, booking);
        if(file!=null) {
            repository.delete(file);
            return true;
        }
        return false;
    }

    private static Reservation toReservation(ReservationFile file) {
        Reservation reservation = new Reservation();
        reservation.setCustomerId(file.getCustomerId());
        reservation.setId(file.getReservationId());
        
        for(int i=0; i<file.getFlights().size(); i++)
            file.getFlights().get(i);
        
        reservation.setFlights(
                file.getFlights().stream()
                    .map(CustomerFileJpaImpl::toFlight)
                    .collect(Collectors.toList())
                );
        
        return reservation;
    }

    private static ReservationFile toFile(Reservation booking) {
        ReservationFile record = new ReservationFile();
        record.setReservationId(booking.getId());
        record.setCustomerId(booking.getCustomerId());
        record.setTravelDate(booking.getFlights().get(0).getDate());

        record.setFlights(
                booking.getFlights().stream()
                    .map(CustomerFileJpaImpl::toFlightEntity)
                    .collect(Collectors.toList())
                    
                );
        return record;
    }
    
    private static Flight toFlight(FlightEntity entity) {
        Flight flight = new Flight();
        flight.setDate(entity.getDate());
        flight.setOrigin(entity.getOrigin());
        flight.setDestination(entity.getDestination());
        flight.setNumber(entity.getNumber());
        return flight;
    }
    
    private static FlightEntity toFlightEntity(Flight flight) {
        FlightEntity entity = new FlightEntity();
        entity.setDate(flight.getDate());
        entity.setOrigin(flight.getOrigin());
        entity.setDestination(flight.getDestination());
        entity.setNumber(flight.getNumber());
        return entity;
    }
}
