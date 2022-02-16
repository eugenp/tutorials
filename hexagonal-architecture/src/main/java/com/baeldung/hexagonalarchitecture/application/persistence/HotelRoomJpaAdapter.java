package com.baeldung.hexagonalarchitecture.application.persistence;

import org.springframework.stereotype.Component;

import com.baeldung.hexagonalarchitecture.domain.HotelRoom;
import com.baeldung.hexagonalarchitecture.domain.HotelRoomRepository;

@Component
public class HotelRoomJpaAdapter implements HotelRoomRepository {

    private final HotelRoomJpaRepository hotelRoomJpaRepository;
    private final HotelRoomJpaMapper hotelRoomJpaMapper;

    public HotelRoomJpaAdapter(HotelRoomJpaRepository hotelRoomJpaRepository, HotelRoomJpaMapper hotelRoomJpaMapper) {
        this.hotelRoomJpaRepository = hotelRoomJpaRepository;
        this.hotelRoomJpaMapper = hotelRoomJpaMapper;
    }

    @Override
    public HotelRoom findByRoomNumber(String roomNumber) {
        JpaHotelRoom jpaModel = hotelRoomJpaRepository.findById(roomNumber)
            .orElseThrow(() -> new IllegalArgumentException("could not find hotel room with roomNumber: " + roomNumber));
        return hotelRoomJpaMapper.fromJpaHotelRoom(jpaModel);
    }

    @Override
    public void save(HotelRoom hotelRoom) {
        JpaHotelRoom jpaModel = hotelRoomJpaMapper.toJpaHotelRoom(hotelRoom);
        hotelRoomJpaRepository.save(jpaModel);
    }
}
