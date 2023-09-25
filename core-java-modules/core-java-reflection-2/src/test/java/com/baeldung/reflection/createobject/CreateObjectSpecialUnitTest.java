package com.baeldung.reflection.createobject;

import com.baeldung.reflection.createobject.special.MaintenanceJob;
import com.baeldung.reflection.createobject.special.PaintJob;
import com.baeldung.reflection.createobject.special.PlatinumJobCard;
import com.baeldung.reflection.createobject.special.RepairJob;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

public class CreateObjectSpecialUnitTest {
    @Test
    public void givenPlatinumJobCard_whenJobTypeRepairMaintenanceAndPaint_thenStartJob() throws InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {

        PlatinumJobCard<RepairJob> platinumJobCard1 = new PlatinumJobCard<RepairJob>();
        platinumJobCard1.setJobType(RepairJob.class);
        assertEquals("Start Platinum Repair Job", platinumJobCard1.startJob());

        PlatinumJobCard<MaintenanceJob> platinumJobCard2 = new PlatinumJobCard<MaintenanceJob>();
        platinumJobCard2.setJobType(MaintenanceJob.class);
        assertEquals("Start Platinum Maintenance Job", platinumJobCard2.startJob());

        PlatinumJobCard<PaintJob> platinumJobCard3 = new PlatinumJobCard<PaintJob>();
        platinumJobCard3.setJobType(PaintJob.class);
        assertEquals("Start Platinum Paint Job", platinumJobCard3.startJob());
    }
}
