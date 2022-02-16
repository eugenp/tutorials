package com.baeldung.hexagonalarchitecture;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.baeldung.hexagonalarchitecture.application.persistence.HotelRoomJpaRepository;
import com.baeldung.hexagonalarchitecture.application.persistence.JpaHotelRoom;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private HotelRoomJpaRepository hotelRoomJpaRepository;

    public StartupApplicationListener(HotelRoomJpaRepository hotelRoomJpaRepository) {
        this.hotelRoomJpaRepository = hotelRoomJpaRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        var room = new JpaHotelRoom();
        room.setRoomNumber("101A");
        room.setStatus(JpaHotelRoom.Status.AVAILABLE);
        hotelRoomJpaRepository.save(room);

        var unavailableRoom = new JpaHotelRoom();
        unavailableRoom.setRoomNumber("102A");
        unavailableRoom.setStatus(JpaHotelRoom.Status.UNAVAILABLE);
        hotelRoomJpaRepository.save(unavailableRoom);
    }
}
