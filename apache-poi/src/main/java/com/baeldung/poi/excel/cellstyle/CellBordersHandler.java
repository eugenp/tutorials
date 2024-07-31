package com.baeldung.poi.excel.cellstyle;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

public class CellBordersHandler {

    public void setRegionBorder(CellRangeAddress region, Sheet sheet, BorderStyle borderStyle) {
        RegionUtil.setBorderTop(borderStyle, region, sheet);
        RegionUtil.setBorderBottom(borderStyle, region, sheet);
        RegionUtil.setBorderLeft(borderStyle, region, sheet);
        RegionUtil.setBorderRight(borderStyle, region, sheet);
    }

    public void setRegionBorderWithColor(CellRangeAddress region, Sheet sheet, BorderStyle borderStyle, short color) {
        RegionUtil.setTopBorderColor(color, region, sheet);
        RegionUtil.setBottomBorderColor(color, region, sheet);
        RegionUtil.setLeftBorderColor(color, region, sheet);
        RegionUtil.setRightBorderColor(color, region, sheet);
        RegionUtil.setBorderTop(borderStyle, region, sheet);
        RegionUtil.setBorderBottom(borderStyle, region, sheet);
        RegionUtil.setBorderLeft(borderStyle, region, sheet);
        RegionUtil.setBorderRight(borderStyle, region, sheet);
    }

    public void setCrazyBorder(CellRangeAddress region, Sheet sheet) {
        RegionUtil.setTopBorderColor(IndexedColors.RED.index, region, sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.GREEN.index, region, sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.BLUE.index, region, sheet);
        RegionUtil.setRightBorderColor(IndexedColors.VIOLET.index, region, sheet);
        RegionUtil.setBorderTop(BorderStyle.DASH_DOT, region, sheet);
        RegionUtil.setBorderBottom(BorderStyle.DOUBLE, region, sheet);
        RegionUtil.setBorderLeft(BorderStyle.DOTTED, region, sheet);
        RegionUtil.setBorderRight(BorderStyle.SLANTED_DASH_DOT, region, sheet);
    }
}
