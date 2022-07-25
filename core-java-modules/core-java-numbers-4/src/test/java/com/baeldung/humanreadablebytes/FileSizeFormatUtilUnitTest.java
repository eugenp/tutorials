package com.baeldung.humanreadablebytes;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class FileSizeFormatUtilUnitTest {
    private final static Map<Long, String> DATA_MAP_BINARY_PREFIXES = new HashMap<Long, String>() {{
        put(0L, "0 Bytes");
        put(1023L, "1023 Bytes");
        put(1024L, "1 KiB");
        put(12_345L, "12.06 KiB");
        put(10_123_456L, "9.65 MiB");
        put(10_123_456_798L, "9.43 GiB");
        put(1_777_777_777_777_777_777L, "1.54 EiB");
    }};

    private final static Map<Long, String> DATA_MAP_SI_PREFIXES = new HashMap<Long, String>() {{
        put(0L, "0 Bytes");
        put(999L, "999 Bytes");
        put(1000L, "1 KB");
        put(12_345L, "12.35 KB");
        put(10_123_456L, "10.12 MB");
        put(10_123_456_798L, "10.12 GB");
        put(1_777_777_777_777_777_777L, "1.78 EB");
    }};
    
    @Test
    public void givenBytes_whenCalltoHumanReadableBinaryPrefixesMethod_thenGetExpectedResults() {
        DATA_MAP_BINARY_PREFIXES.forEach((in, expected) -> Assert.assertEquals(expected, FileSizeFormatUtil.toHumanReadableBinaryPrefixes(in)));
    }
    
    @Test
    public void givenBytes_whenCalltoHumanReadableSIPrefixesMethod_thenGetExpectedResults() {
    	DATA_MAP_SI_PREFIXES.forEach((in, expected) -> Assert.assertEquals(expected, FileSizeFormatUtil.toHumanReadableSIPrefixes(in)));
    }

    @Test
    public void givenBytes_whenCalltoHumanReadableBinaryPrefixesWithEnumMethod_thenGetExpectedResults() {
        DATA_MAP_BINARY_PREFIXES.forEach((in, expected) -> Assert.assertEquals(expected, FileSizeFormatUtil.toHumanReadableBinaryPrefixesWithEnum(in)));
    }

    @Test
    public void givenBytes_whenCalltoHumanReadableSIPrefixesWithEnumMethod_thenGetExpectedResults() {
    	DATA_MAP_SI_PREFIXES.forEach((in, expected) -> Assert.assertEquals(expected, FileSizeFormatUtil.toHumanReadableSIPrefixesWithEnum(in)));
    }
    
    @Test
    public void givenBytes_whenCalltoHumanReadableByLeadingZeros_thenGetExpectedResults() {
        DATA_MAP_BINARY_PREFIXES.forEach((in, expected) -> Assert.assertEquals(expected, FileSizeFormatUtil.toHumanReadableByNumOfLeadingZeros(in)));
    }
    
    @Test
    public void givenBytes_whenCalltoHumanReadableByFileUtils_thenOutputExpectedResults() {
        DATA_MAP_BINARY_PREFIXES.forEach((in, expected) -> System.out.println(in + " bytes -> " + FileUtils.byteCountToDisplaySize(in)));
    }
}
