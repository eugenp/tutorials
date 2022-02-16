package com.baeldung.hexagonalarchitecture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.baeldung.hexagonalarchitecture.domain.Booking;
import com.baeldung.hexagonalarchitecture.domain.HotelRoom;
import com.baeldung.hexagonalarchitecture.domain.HotelRoomBookingService;
import com.baeldung.hexagonalarchitecture.domain.HotelRoomRepository;
import com.baeldung.hexagonalarchitecture.domain.StaffMembersNotification;
import com.baeldung.hexagonalarchitecture.domain.StaffMembersNotifier;

@ExtendWith(MockitoExtension.class)
public class HotelRoomBookingServiceTest {

    @Mock
    private StaffMembersNotifier staffMembersNotifier;
    private HotelRoomRepository hotelRoomBookingRepository;
    private HotelRoomBookingService hotelRoomBookingService;

    @BeforeEach
    void beforeEach() {
        hotelRoomBookingRepository = new HotelRoomRepositoryTestImpl();
        hotelRoomBookingService = new HotelRoomBookingService(hotelRoomBookingRepository, staffMembersNotifier);
    }

    @Test
    void test() {
        hotelRoomBookingRepository.save(new HotelRoom("101A"));

        hotelRoomBookingService.addBooking("101A", aBooking());

        Booking savedBooking = getSavedBooking("101A", "6246fe1c-8c49-11ec-a8a3-0242ac120002");
        assertThat(savedBooking.getBookingName()
            .get()).isEqualTo("John DOE");
        assertThat(savedBooking.getPeriodDetails()
            .getPeriodInDays()).isEqualTo(3);
    }

    @Test
    void tst_illegalState() {
        hotelRoomBookingRepository.save(new HotelRoom("101A"));
        hotelRoomBookingService.startRoutineMaintenance("101A");

        assertThatThrownBy(() -> hotelRoomBookingService.addBooking("101A", aBooking())).isInstanceOf(IllegalStateException.class)
            .hasMessage("the room 101A is not AVAILABLE for booking.");
    }

    @Test
    void tst_maintainnace() {
        hotelRoomBookingRepository.save(new HotelRoom("101A"));
        hotelRoomBookingService.startRoutineMaintenance("101A");

        verify(staffMembersNotifier).sendNotification(argThat(notification -> notification.getRoomNumber()
            .equals("101A") && notification.getTeam() == StaffMembersNotification.Team.MAINTENANCE));
    }

    private Booking getSavedBooking(String roomNumber, String bookingUuid) {
        return hotelRoomBookingRepository.findByRoomNumber(roomNumber)
            .getBookings()
            .get(UUID.fromString(bookingUuid));
    }

    private Booking aBooking() {
        var startDate = LocalDate.of(2022, Month.JANUARY, 1);
        var endDate = LocalDate.of(2022, Month.JANUARY, 4);
        var uuid = UUID.fromString("6246fe1c-8c49-11ec-a8a3-0242ac120002");
        return new Booking(uuid, new Booking.FullName("John", "Doe"), new Booking.PeriodDetails(startDate, endDate));
    }
}
