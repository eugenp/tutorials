package com.baeldung.jackson.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public class City {

    public static class CityWithDefaultEnum {

        private Distance distance;

        public Distance getDistance() {
            return distance;
        }

        public void setDistance(Distance distance) {
            this.distance = distance;
        }

        public enum Distance {

            KILOMETER("km", 1000), MILE("miles", 1609.34), METER("meters", 1), INCH("inches", 0.0254), CENTIMETER("cm", 0.01), MILLIMETER("mm", 0.001);

            private String unit;
            private final double meters;

            private Distance(String unit, double meters) {
                this.unit = unit;
                this.meters = meters;
            }

            public double getMeters() {
                return meters;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }
        }
    }
  
    public static class CityWithJsonCreatorEnum {

        private Distance distance;

        public Distance getDistance() {
            return distance;
        }

        public void setDistance(Distance distance) {
            this.distance = distance;
        }

        public enum Distance {

            KILOMETER("km", 1000), MILE("miles", 1609.34), METER("meters", 1), INCH("inches", 0.0254), CENTIMETER("cm", 0.01), MILLIMETER("mm", 0.001);

            private String unit;
            private final double meters;

            private Distance(String unit, double meters) {
                this.unit = unit;
                this.meters = meters;
            }

            public double getMeters() {
                return meters;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            @JsonCreator
            public static Distance forValues(@JsonProperty("unit") String unit, @JsonProperty("meters") double meters) {

                for (Distance distance : Distance.values()) {
                    if (distance.unit.equals(unit) && Double.compare(distance.meters, meters) == 0) {

                        return distance;
                    }
                }

                return null;

            }
        }
    }

    public static class CityWithJsonPropertyEnum {

        private Distance distance;

        public Distance getDistance() {
            return distance;
        }

        public void setDistance(Distance distance) {
            this.distance = distance;
        }

        public enum Distance {

            @JsonProperty("distance-in-km")
            KILOMETER("km", 1000), 
            
            @JsonProperty("distance-in-miles")
            MILE("miles", 1609.34),
            
            @JsonProperty("distance-in-meters")
            METER("meters", 1), 
            
            @JsonProperty("distance-in-inches")
            INCH("inches", 0.0254), 
            
            @JsonProperty("distance-in-cm")
            CENTIMETER("cm", 0.01), 
            
            @JsonProperty("distance-in-mm")
            MILLIMETER("mm", 0.001);

            private String unit;
            private final double meters;

            private Distance(String unit, double meters) {
                this.unit = unit;
                this.meters = meters;
            }

            public double getMeters() {
                return meters;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

        }
    }

    public static class CityWithJsonValueEnum {

        private Distance distance;

        public Distance getDistance() {
            return distance;
        }

        public void setDistance(Distance distance) {
            this.distance = distance;
        }

        public enum Distance {

            KILOMETER("km", 1000), MILE("miles", 1609.34), METER("meters", 1), INCH("inches", 0.0254), CENTIMETER("cm", 0.01), MILLIMETER("mm", 0.001);

            private String unit;
            private final double meters;

            private Distance(String unit, double meters) {
                this.unit = unit;
                this.meters = meters;
            }

            @JsonValue
            public double getMeters() {
                return meters;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

        }
    }

}
