package com.baeldung.hexagonalarchitecture.domain;

import static com.baeldung.hexagonalarchitecture.domain.StaffMembersNotification.Team.CLEANING;
import static com.baeldung.hexagonalarchitecture.domain.StaffMembersNotification.Team.MAINTENANCE;

import org.springframework.stereotype.Service;

@Service
public class HotelRoomBookingService {

    private HotelRoomRepository hotelRoomRepository;
    private StaffMembersNotifier staffNotifier;

    public HotelRoomBookingService(HotelRoomRepository hotelRoomRepository, StaffMembersNotifier staffNotifier) {
        this.hotelRoomRepository = hotelRoomRepository;
        this.staffNotifier = staffNotifier;
    }

    public Booking addBooking(String roomNumber, Booking booking) {
        HotelRoom room = hotelRoomRepository.findByRoomNumber(roomNumber);
        room.book(booking);

        hotelRoomRepository.save(room);
        return booking;
    }

    public HotelRoom startRoutineMaintenance(String roomNumber) {
        HotelRoom room = hotelRoomRepository.findByRoomNumber(roomNumber);
        room.startRoutineMaintenance();
        staffNotifier.sendNotification(new StaffMembersNotification(MAINTENANCE, roomNumber));

        hotelRoomRepository.save(room);
        return room;
    }

    public HotelRoom startHospitalityCleaning(String roomNumber) {
        HotelRoom room = hotelRoomRepository.findByRoomNumber(roomNumber);
        room.startHospitalityCleaning();
        staffNotifier.sendNotification(new StaffMembersNotification(CLEANING, roomNumber));

        hotelRoomRepository.save(room);
        return room;
    }
}
