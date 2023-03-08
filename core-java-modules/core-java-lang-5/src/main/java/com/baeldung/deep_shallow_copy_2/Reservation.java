package com.baeldung.deep_shallow_copy_2;

import java.util.Date;
import java.util.Objects;

public class Reservation implements Cloneable {
    private Long reservationId;
    private FlightInfo flightInfo;

    @Override
    public Reservation clone() {
        try {
            Reservation clone = (Reservation) super.clone();
            FlightInfo fi = clone.getFlightInfo();
            clone.setReservationId(clone.getReservationId());
            clone.setFlightInfo(
              new FlightInfo(
                fi.getFlightId(),
                fi.getIataNumber(),
                (Date) fi.getDepartureDate().clone(),
                (Date) fi.getArrivalDate().clone())
            );
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public FlightInfo getFlightInfo() {
        return flightInfo;
    }

    public void setFlightInfo(FlightInfo flightInfo) {
        this.flightInfo = flightInfo;
    }
}

class FlightInfo {
    private Long flightId;
    private String iataNumber;
    private Date departureDate;
    private Date arrivalDate;

    public FlightInfo(Long flightId, String iataNumber, Date departureDate, Date arrivalDate) {
        this.flightId = flightId;
        this.iataNumber = iataNumber;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightInfo that = (FlightInfo) o;
        return Objects.equals(flightId, that.flightId) &&
          Objects.equals(iataNumber, that.iataNumber) &&
          Objects.equals(departureDate, that.departureDate) &&
          Objects.equals(arrivalDate, that.arrivalDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightId, iataNumber, departureDate, arrivalDate);
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public String getIataNumber() {
        return iataNumber;
    }

    public void setIataNumber(String iataNumber) {
        this.iataNumber = iataNumber;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
}