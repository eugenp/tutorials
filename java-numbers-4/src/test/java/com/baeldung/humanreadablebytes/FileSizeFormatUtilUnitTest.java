package com.baeldung.humanreadablebytes;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class FileSizeFormatUtilUnitTest {
    private final static Map<Long, String> DATA_MAP = new HashMap<Long, String>() {{
        put(0L, "0 Bytes");
        put(1023L, "1023 Bytes");
        put(1024L, "1 KB");
        put(12_345L, "12.06 KB");
        put(10_123_456L, "9.65 MB");
        put(10_123_456_798L, "9.43 GB");
        put(1_777_777_777_777_777_777L, "1.54 EB");
    }};

    @Test
    public void givenBytes_whenCalltoHumanReadableMethod_thenGetExpectedResults() {
        DATA_MAP.forEach((in, expected) -> Assert.assertEquals(expected, FileSizeFormatUtil.toHumanReadable(in)));
    }

    @Test
    public void givenBytes_whenCalltoHumanReadableWithEnumMethod_thenGetExpectedResults() {
        DATA_MAP.forEach((in, expected) -> Assert.assertEquals(expected, FileSizeFormatUtil.toHumanReadableWithEnum(in)));
    }

    @Test
    public void givenBytes_whenCalltoHumanReadableByLeadingZeros_thenGetExpectedResults() {
        DATA_MAP.forEach((in, expected) -> Assert.assertEquals(expected, FileSizeFormatUtil.toHumanReadableByNumOfLeadingZeros(in)));
    }

    @Test
    public void givenBytes_whenCalltoHumanReadableByFileUtils_thenOutputExpectedResults() {
        DATA_MAP.forEach((in, expected) -> System.out.println(in + " bytes -> " + FileUtils.byteCountToDisplaySize(in)));
    }
}
