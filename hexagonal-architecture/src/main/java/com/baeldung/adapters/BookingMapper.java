package com.baeldung.adapters;

import com.baeldung.domain.Booking;
import org.apache.ibatis.annotations.Param;

interface BookingMapper {
    void insert(@Param("booking") Booking booking);
}
