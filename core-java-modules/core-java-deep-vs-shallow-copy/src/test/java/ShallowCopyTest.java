import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ShallowCopyTest {

    @Test
    public void shallowCopy() {
        Setting originalSetting = new Setting("MaxConnections", "120");
        SystemConfig originalConfig = new SystemConfig(1, originalSetting);

        //        Create a Shallow copy
        SystemConfig shallowCopyConfig = originalConfig;
        shallowCopyConfig.setId(2);
        shallowCopyConfig.setSetting(new Setting("Timeout", "20s"));

        //        Assert if changes affect the original object
        assertEquals(2, originalConfig.getId());
        assertEquals("Setting{name='Timeout', value='20s'}", originalConfig.getSetting()
            .toString());
    }
}
