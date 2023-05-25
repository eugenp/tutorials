package com.baeldung.humanreadablebytes;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileSizeFormatUtil {
    private static final long BYTE = 1L;
    private static final long KiB = BYTE << 10;
    private static final long MiB = KiB << 10;
    private static final long GiB = MiB << 10;
    private static final long TiB = GiB << 10;
    private static final long PiB = TiB << 10;
    private static final long EiB = PiB << 10;
    
    private static final long KB = BYTE * 1000;
    private static final long MB = KB * 1000;
    private static final long GB = MB * 1000;
    private static final long TB = GB * 1000;
    private static final long PB = TB * 1000;
    private static final long EB = PB * 1000;
    
    private static final DecimalFormat DEC_FORMAT = new DecimalFormat("#.##");

    public static String toHumanReadableBinaryPrefixes(long size) {
        if (size < 0)
            throw new IllegalArgumentException("Invalid file size: " + size);
        if (size >= EiB) return formatSize(size, EiB, "EiB");
        if (size >= PiB) return formatSize(size, PiB, "PiB");
        if (size >= TiB) return formatSize(size, TiB, "TiB");
        if (size >= GiB) return formatSize(size, GiB, "GiB");
        if (size >= MiB) return formatSize(size, MiB, "MiB");
        if (size >= KiB) return formatSize(size, KiB, "KiB");
        return formatSize(size, BYTE, "Bytes");
    }

    public static String toHumanReadableSIPrefixes(long size) {
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

    public static String toHumanReadableBinaryPrefixesWithEnum(long size) {
        final List<SizeUnitBinaryPrefixes> units = SizeUnitBinaryPrefixes.unitsInDescending();
        if (size < 0)
            throw new IllegalArgumentException("Invalid file size: " + size);
        String result = null;
        for (SizeUnitBinaryPrefixes unit : units) {
            if (size >= unit.getUnitBase()) {
                result = formatSize(size, unit.getUnitBase(), unit.name());
                break;
            }
        }
        return result == null ? formatSize(size, SizeUnitBinaryPrefixes.Bytes.getUnitBase(), SizeUnitBinaryPrefixes.Bytes.name()) : result;
    }
    
    public static String toHumanReadableSIPrefixesWithEnum(long size) {
        final List<SizeUnitSIPrefixes> units = SizeUnitSIPrefixes.unitsInDescending();
        if (size < 0)
            throw new IllegalArgumentException("Invalid file size: " + size);
        String result = null;
        for (SizeUnitSIPrefixes unit : units) {
            if (size >= unit.getUnitBase()) {
                result = formatSize(size, unit.getUnitBase(), unit.name());
                break;
            }
        }
        return result == null ? formatSize(size, SizeUnitSIPrefixes.Bytes.getUnitBase(), SizeUnitSIPrefixes.Bytes.name()) : result;
    }

    public static String toHumanReadableByNumOfLeadingZeros(long size) {
        if (size < 0)
            throw new IllegalArgumentException("Invalid file size: " + size);
        if (size < 1024) return size + " Bytes";
        int unitIdx = (63 - Long.numberOfLeadingZeros(size)) / 10;
        return formatSize(size, 1L << (unitIdx * 10), " KMGTPE".charAt(unitIdx) + "iB");
    }
    
    enum SizeUnitBinaryPrefixes {
        Bytes(1L),
        KiB(Bytes.unitBase << 10),
        MiB(KiB.unitBase << 10),
        GiB(MiB.unitBase << 10),
        TiB(GiB.unitBase << 10),
        PiB(TiB.unitBase << 10),
        EiB(PiB.unitBase << 10);

        private final Long unitBase;

        public static List<SizeUnitBinaryPrefixes> unitsInDescending() {
            List<SizeUnitBinaryPrefixes> list = Arrays.asList(values());
            Collections.reverse(list);
            return list;
        }

        public Long getUnitBase() {
            return unitBase;
        }

        SizeUnitBinaryPrefixes(long unitBase) {
            this.unitBase = unitBase;
        }
    }
    
    enum SizeUnitSIPrefixes {
        Bytes(1L),
        KB(Bytes.unitBase * 1000),
        MB(KB.unitBase * 1000),
        GB(MB.unitBase * 1000),
        TB(GB.unitBase * 1000),
        PB(TB.unitBase * 1000),
        EB(PB.unitBase * 1000);

        private final Long unitBase;

        public static List<SizeUnitSIPrefixes> unitsInDescending() {
            List<SizeUnitSIPrefixes> list = Arrays.asList(values());
            Collections.reverse(list);
            return list;
        }

        public Long getUnitBase() {
            return unitBase;
        }

        SizeUnitSIPrefixes(long unitBase) {
            this.unitBase = unitBase;
        }
    }
}
