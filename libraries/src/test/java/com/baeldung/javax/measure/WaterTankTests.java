package com.baeldung.javax.measure;

import javax.measure.Measure;
import javax.measure.converter.UnitConverter;
import javax.measure.quantity.Duration;
import javax.measure.quantity.Length;
import javax.measure.quantity.Pressure;
import javax.measure.quantity.Volume;
import static javax.measure.unit.NonSI.HOUR;
import static javax.measure.unit.NonSI.LITRE;
import static javax.measure.unit.NonSI.MILE;
import static javax.measure.unit.NonSI.MINUTE;
import javax.measure.unit.SI;
import static javax.measure.unit.SI.KILO;
import static javax.measure.unit.SI.METER;
import static javax.measure.unit.SI.NEWTON;
import static javax.measure.unit.SI.SECOND;
import javax.measure.unit.Unit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class WaterTankTests {

    @Test
    public void givenMeasure_whenGetUnitAndConvertValue_thenSuccess() {
        WaterTank waterTank = new WaterTank();
        waterTank.setCapacityMeasure(Measure.valueOf(9.2, LITRE));
        assertEquals(LITRE, waterTank.getCapacityMeasure().getUnit());

        Measure<Volume> waterCapacity = waterTank.getCapacityMeasure();
        double volumeInLitre = waterCapacity.getValue().doubleValue();
        assertEquals(9.2, volumeInLitre, 0.0f);

        double volumeInMilliLitre = waterCapacity.doubleValue(SI.MILLI(LITRE));
        assertEquals(9200.0, volumeInMilliLitre, 0.0f);

        Unit<Length> Kilometer = SI.KILO(METER);
        Unit<Length> Centimeter = SI.CENTI(METER);
    }

    @Test
    public void givenMeasure_whenAlternateMeasure_ThenGetAlternateMeasure() {
        Unit<Pressure> PASCAL = NEWTON.divide(METER.pow(2)).alternate("Pa");
        assertTrue(Unit.valueOf("Pa").equals(PASCAL));
    }

    @Test
    public void givenMeasure_whenCompoundMeasure_ThenGetCompoundMeasure() {
        Unit<Duration> HOUR_MINUTE_SECOND = HOUR.compound(MINUTE).compound(SECOND);
        Measure<Duration> duration = Measure.valueOf(12345, SECOND);
        assertEquals("3h25min45s", duration.to(HOUR_MINUTE_SECOND).toString());
    }

    @Test
    public void givenMiles_whenConvertToKilometer_ThenConverted() {
        double distanceInMiles = 50.0;
        UnitConverter mileToKilometer = MILE.getConverterTo(KILO(METER));
        double distanceInKilometers = mileToKilometer.convert(distanceInMiles);
        assertEquals(80.4672, distanceInKilometers, 0.00f);
    }

    @Test
    public void givenSymbol_WhenCompareToSystemUnit_ThenSuccess() {
        assertTrue(Unit.valueOf("kW").equals(SI.KILO(SI.WATT)));
        assertTrue(Unit.valueOf("ms").equals(SI.SECOND.divide(1000)));
    }
}
