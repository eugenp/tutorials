package com.baeldung.methodmultiplereturnvalues;

class Coordinates {

    private final double longitude;
    private final double latitude;
    private final String directionsNote;
 
    public Coordinates(double longitude, double latitude, String directionsNote) {
        
        this.longitude = longitude;
        this.latitude = latitude;
        this.directionsNote = directionsNote;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getDirectionsNote() {
        return directionsNote;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((directionsNote == null) ? 0 : directionsNote.hashCode());
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Coordinates other = (Coordinates) obj;
        if (directionsNote == null) {
            if (other.directionsNote != null)
                return false;
        } else if (!directionsNote.equals(other.directionsNote))
            return false;
        if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
            return false;
        if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
            return false;
        return true;
    }

}
