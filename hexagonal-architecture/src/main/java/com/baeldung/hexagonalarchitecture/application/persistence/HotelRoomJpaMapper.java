package com.baeldung.hexagonalarchitecture.application.persistence;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.baeldung.hexagonalarchitecture.domain.Booking;
import com.baeldung.hexagonalarchitecture.domain.HotelRoom;

@Component
public class HotelRoomJpaMapper {

    private Map<HotelRoom.Status, JpaHotelRoom.Status> statusMapping = Map.of(HotelRoom.Status.AVAILABLE, JpaHotelRoom.Status.AVAILABLE, HotelRoom.Status.HOSPITALITY_CLEANING, JpaHotelRoom.Status.CLEANING, HotelRoom.Status.TEMPORARY_UNAVAILABLE,
        JpaHotelRoom.Status.UNAVAILABLE, HotelRoom.Status.ROUTINE_MAINTENANCE, JpaHotelRoom.Status.MAINTENANCE);

    public HotelRoom fromJpaHotelRoom(JpaHotelRoom jpaModel) {
        HotelRoom.Status status = fromJpaRoomStatus(jpaModel.getStatus());
        Map<UUID, Booking> bookings = jpaModel.getBookings()
            .stream()
            .map(this::fromJpaBooking)
            .collect(Collectors.toMap(Booking::getUuid, booking -> booking));
        return new HotelRoom(jpaModel.getRoomNumber(), status, bookings);
    }

    public JpaHotelRoom toJpaHotelRoom(HotelRoom hotelRoom) {
        JpaHotelRoom jpaModel = new JpaHotelRoom();
        jpaModel.setRoomNumber(hotelRoom.getRoomNumber());
        jpaModel.setStatus(toJpaRoomStatus(hotelRoom.getStatus()));

        Set<JpaBooking> jpaBookings = hotelRoom.getBookings()
            .values()
            .stream()
            .map(this::toJpaBooking)
            .collect(Collectors.toSet());
        jpaModel.setBookings(jpaBookings);
        return jpaModel;
    }

    private Booking fromJpaBooking(JpaBooking jpaModel) {
        Booking.FullName fullName = new Booking.FullName(jpaModel.getFirstName(), jpaModel.getLastName());
        Booking.PeriodDetails periodDetails = new Booking.PeriodDetails(jpaModel.getStartDate(), jpaModel.getEndDate());
        UUID uuid = UUID.fromString(jpaModel.getUuid());
        return new Booking(uuid, fullName, periodDetails);
    }

    private JpaBooking toJpaBooking(Booking booking) {
        JpaBooking jpaModel = new JpaBooking();
        jpaModel.setUuid(booking.getUuid()
            .toString());
        jpaModel.setFirstName(booking.getBookingName()
            .getFirstName());
        jpaModel.setLastName(booking.getBookingName()
            .getLastName());
        jpaModel.setStartDate(booking.getPeriodDetails()
            .getStartDate());
        jpaModel.setEndDate(booking.getPeriodDetails()
            .getEndDate());
        return jpaModel;
    }

    private JpaHotelRoom.Status toJpaRoomStatus(HotelRoom.Status jpaModelStatus) {
        return statusMapping.get(jpaModelStatus);
    }

    private HotelRoom.Status fromJpaRoomStatus(JpaHotelRoom.Status jpaModelStatus) {
        return statusMapping.entrySet()
            .stream()
            .filter(entry -> entry.getValue() == jpaModelStatus)
            .map(Map.Entry::getKey)
            .findFirst()
            .orElseThrow();
    }
}
