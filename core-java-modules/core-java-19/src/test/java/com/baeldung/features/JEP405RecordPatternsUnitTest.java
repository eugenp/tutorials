package com.baeldung.features;

import com.baeldung.features.records.GPSPoint;
import com.baeldung.features.records.Location;
import com.baeldung.features.records.LocationWrapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class JEP405RecordPatternsUnitTest {

    Object object = new Location("Home", new GPSPoint(1.0, 2.0));

    @Test
    void givenObject_whenTestInstanceOfAndCastIdiom_shouldMatchNewInstanceOf() {
        // old Code
        if (object instanceof Location) {
            Location l = (Location) object;
            assertThat(l).isInstanceOf(Location.class);
        }
        // new code
        if (object instanceof Location l) {
            assertThat(l).isInstanceOf(Location.class);
        }
    }

    @Test
    void givenObject_whenTestDestruct_shouldMatch() {
        // when
        if (object instanceof Location(var name, var gpsPoint)) {
            // then
            assertThat(name).isEqualTo("Home");
            assertThat(gpsPoint).isInstanceOf(GPSPoint.class);
        }

        if (object instanceof Location(var name, GPSPoint(var lat, var lng))) {
            assertThat(lat).isEqualTo(1.0);
            assertThat(lng).isEqualTo(2.0);
        }
    }

    @Test
    void givenObjectIsNull_whenTestNullCheck_shouldNotMatch() {
        Location l = null;
        if (l instanceof Location location) {
            assertThat(location).isNotNull();
        }
    }


    @Test
    void givenObject_whenTestGenericTypeInstanceOf_shouldMatch() {
        LocationWrapper<Location> locationWrapper = new LocationWrapper<>(new Location("Home", new GPSPoint(1.0, 2.0)), "Description");
        if (locationWrapper instanceof LocationWrapper<Location>(var ignored, var description)) {
            assertThat(description).isEqualTo("Description");
        }
    }


    @Test
    void givenObject_whenTestSwitchExpressionWithTypePattern_shouldMatch() {
        String result = switch (object) {
            case Location l -> l.name();
            default -> "default";
        };
        assertThat(result).isEqualTo("Home");
        Double result2 = switch (object) {
            case Location(var name, GPSPoint(var lat, var lng)) -> lat;
            default -> 0.0;
        };
        assertThat(result2).isEqualTo(1.0);
    }

    @Test
    void givenObject_whenTestGuardedSwitchExpressionWithTypePattern_shouldMatchAndGuard() {
        String result = switch (object) {
            case Location(var name, var ignored) when name.equals("Home") -> "Test";
            case Location(var name, var ignored) -> name;
            default -> "default";
        };
        assertThat(result).isEqualTo("Test");

        String otherResult = switch (new Location("Other", new GPSPoint(1.0, 2.0))) {
            case Location(var name, var ignored) when name.equals("Home") -> "Test";
            case Location(var name, var ignored) -> name;
            default -> "default";
        };
        assertThat(otherResult).isEqualTo("Other");

        Object noLocation = new GPSPoint(1.0, 2.0);
        String noLocationResult = switch (noLocation) {
            case Location(var name, var ignored) when name.equals("Home") -> "Test";
            case Location(var name, var ignored) -> name;
            default -> "default";
        };
        assertThat(noLocationResult).isEqualTo("default");
    }
}
