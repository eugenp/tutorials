package com.baeldung.humanreadablebytes;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileSizeFormatUtil {
    private static final long BYTE = 1L;
    private static final long KB = BYTE << 10;
    private static final long MB = KB << 10;
    private static final long GB = MB << 10;
    private static final long TB = GB << 10;
    private static final long PB = TB << 10;
    private static final long EB = PB << 10;
    private static final DecimalFormat DEC_FORMAT = new DecimalFormat("#.##");

    public static String toHumanReadable(long size) {
        if (size < 0)
            throw new IllegalArgumentException("Invalid file size: " + size);
        if (size >= EB) return formatSize(size, EB, "EB");
        if (size >= PB) return formatSize(size, PB, "PB");
        if (size >= TB) return formatSize(size, TB, "TB");
        if (size >= GB) return formatSize(size, GB, "GB");
        if (size >= MB) return formatSize(size, MB, "MB");
        if (size >= KB) return formatSize(size, KB, "KB");
        return formatSize(size, BYTE, "Bytes");
    }

    private static String formatSize(long size, long divider, String unitName) {
        return DEC_FORMAT.format((double) size / divider) + " " + unitName;
    }

    public static String toHumanReadableWithEnum(long size) {
        final List<SizeUnit> units = SizeUnit.unitsInDescending();
        if (size < 0)
            throw new IllegalArgumentException("Invalid file size: " + size);
        String result = null;
        for (SizeUnit unit : units) {
            if (size >= unit.getUnitBase()) {
                result = formatSize(size, unit.getUnitBase(), unit.name());
                break;
            }
        }
        return result == null ? formatSize(size, SizeUnit.Bytes.getUnitBase(), SizeUnit.Bytes.name()) : result;
    }

    public static String toHumanReadableByNumOfLeadingZeros(long size) {
        if (size < 0)
            throw new IllegalArgumentException("Invalid file size: " + size);
        if (size < 1024) return size + " Bytes";
        int unitIdx = (63 - Long.numberOfLeadingZeros(size)) / 10;
        return formatSize(size, 1L << (unitIdx * 10), " KMGTPE".charAt(unitIdx) + "B");
    }

    enum SizeUnit {
        Bytes(1L),
        KB(Bytes.unitBase << 10),
        MB(KB.unitBase << 10),
        GB(MB.unitBase << 10),
        TB(GB.unitBase << 10),
        PB(TB.unitBase << 10),
        EB(PB.unitBase << 10);

        private final Long unitBase;

        public static List<SizeUnit> unitsInDescending() {
            List<SizeUnit> list = Arrays.asList(values());
            Collections.reverse(list);
            return list;
        }

        public Long getUnitBase() {
            return unitBase;
        }

        SizeUnit(long unitBase) {
            this.unitBase = unitBase;
        }
    }
}
