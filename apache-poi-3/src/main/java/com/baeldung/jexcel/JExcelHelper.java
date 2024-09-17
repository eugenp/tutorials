package com.baeldung.jexcel;

import jxl.*;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jxl.read.biff.BiffException;
import java.io.File;
import java.io.IOException;
import jxl.write.*;
import jxl.write.Number;
import jxl.format.Colour;

public class JExcelHelper {
    
    public boolean isRowEmpty(Cell[] row) {
    	if (row == null) {
            return true;
        }
        for (Cell cell : row) {
            if (cell != null && !cell.getContents().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}