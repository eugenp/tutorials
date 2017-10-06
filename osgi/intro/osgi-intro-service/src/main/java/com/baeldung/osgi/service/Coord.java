package com.baeldung.osgi.service;

public class Coord {

    private final double lat;
    private final double lon;

    public Coord(double lat, double lon) {

        if(lat < -90.0 || lat > 90) throw new IllegalArgumentException("Invalid lattude.");
        if(lon < -180.0 || lon > 180) throw new IllegalArgumentException("Invalid longitude.");

        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Coord coord = (Coord) o;

        if (Double.compare(coord.lat, lat) != 0)
            return false;
        return Double.compare(coord.lon, lon) == 0;
    }

    @Override public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(lat);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lon);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override public String toString() {
        return String.format("(%f, %f)", lat, lon);
    }
}
