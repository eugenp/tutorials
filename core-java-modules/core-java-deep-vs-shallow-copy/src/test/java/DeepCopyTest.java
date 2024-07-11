import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DeepCopyTest {

    @Test
    public void deepCopy() {
        Setting originalSetting = new Setting("MaxConnections", "120");
        SystemConfig originalConfig = new SystemConfig(1, originalSetting);

        //        Create a Deep copy
        SystemConfig copiedConfig = new SystemConfig(originalConfig);
        copiedConfig.setId(3);
        copiedConfig.setSetting(new Setting("MemoryLimit", "2GB"));

        //        Assert if original object remains unchanged
        assertEquals(1, originalConfig.getId());
        assertEquals("Setting{name='MaxConnections', value='120'}", originalConfig.getSetting()
            .toString());

        //        Assert if copied object has new values
        assertEquals(3, copiedConfig.getId());
        assertEquals("Setting{name='MemoryLimit', value='2GB'}", copiedConfig.getSetting()
            .toString());
    }
}
